#!/usr/bin/env bash

# Script to determine the best TCR (total_compression_ratio) value.
# This script should be invoked by a concrete implementation's driver/determine-best-tcr.sh script.
#
# Note that a lower TCR is better as it indicates better compression / higher throughput.
#
# Before running, make sure that:
# - you have invoked scripts/backup-database.sh

set -eu
set -o pipefail

BENCHMARK_PROPERTIES_FILE=${1:-driver/benchmark.properties}

TCR_MIN=${2:-0.05}
TCR_MAX=${3:-0.30}
TCR_STEPS=${4:-4}

TCR_CURRENT=${TCR_MIN}

rm -f tcr.log

for i in $(seq 1 ${TCR_STEPS}); do
    echo "[determine-best-tcr.sh] Step ${i}" | tee -a tcr.log
    echo "[determine-best-tcr.sh] - Current TCR value: ${TCR_CURRENT}" | tee -a tcr.log

    # Prepare for the next benchmark run: adjust configuration file, restore database
    sed -i "s/^time_compression_ratio=.*/time_compression_ratio=${TCR_CURRENT}/" ${BENCHMARK_PROPERTIES_FILE}
    scripts/restore-database.sh

    # Conduct benchmark run, save output
    driver/benchmark.sh | tee /tmp/ldbc_snb_interactive_driver_output

    # grep for SCHEDULE AUDIT PASSED, looking for 2 lines
    grep 'SCHEDULE AUDIT' /tmp/ldbc_snb_interactive_driver_output > /tmp/ldbc_snb_schedule_audit
    if [ "$(wc -l < /tmp/ldbc_snb_schedule_audit)" != "2" ]; then
        echo "[determine-best-tcr.sh] Error: Driver output should contain two lines with SCHEDULE AUDIT" | tee -a tcr.log
        break
    fi

    # check the 2nd line (the benchmark run result) to see whether the run passed, adjust TCR accordingly
    if tail -n +2 /tmp/ldbc_snb_schedule_audit | grep 'PASSED SCHEDULE AUDIT'; then
        echo "[determine-best-tcr.sh] Passed schedule audit for TCR value: ${TCR_CURRENT}" | tee -a tcr.log

        if [ ${TCR_CURRENT} = ${TCR_MIN} ]; then
            echo "[determine-best-tcr.sh] Run passed with the minimum TCR value, no need for further runs" | tee -a tcr.log
            break
        fi

        TCR_CURRENT=$(echo "scale=4 ; ${TCR_MIN} + (${TCR_CURRENT} - ${TCR_MIN}) / 2" | bc | awk '{printf "%.4f\n", $0}')
    else
        echo "[determine-best-tcr.sh] Failed schedule audit for TCR value: ${TCR_CURRENT}" | tee -a tcr.log

        TCR_CURRENT=$(echo "scale=4 ; ${TCR_CURRENT} + (${TCR_MAX} - ${TCR_CURRENT}) / 2" | bc | awk '{printf "%.4f\n", $0}')
    fi

done
