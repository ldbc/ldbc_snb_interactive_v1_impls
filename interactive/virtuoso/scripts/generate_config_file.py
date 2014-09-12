#!/usr/bin/python

import sys

if len(sys.argv) != 4:
    print 'Usage:', sys.argv[0], 'updateStream_file', 'number_of_operations', 'output_file'
    exit()

try:
    f = open(sys.argv[1], 'r')
except IOError:
    print 'Error while opening the file', sys.argv[1]
    exit()

number_of_operations = int(sys.argv[2])
current_operation = 0
current_update_operation = 0

while True:
    line = f.readline().strip()
    if line == '':
        exit('There is no enough operations in the file')
    parts = line.split('|')
    time = parts[0]
    type_of_update = parts[1]
    current_operation = current_operation + 1
    if type_of_update == 'ADD_COMMENT' or type_of_update == 'ADD_POST':
        current_operation = current_operation + 6.73
    current_update_operation = current_update_operation + 1
    if current_update_operation == 1:
        number_of_msec_of_first_update = int(time)
    if current_operation >= number_of_operations:
        number_of_msec_of_last_update = int(time)
        break

f.close()

total_duration = number_of_msec_of_last_update - number_of_msec_of_first_update
current_read_operation = current_operation - current_update_operation

#print 'current_operation:', current_operation
#print 'current_update_operation:', current_update_operation
#print 'current_read_operation:', current_read_operation
#print 'number_of_msec_of_first_update:', number_of_msec_of_first_update
#print 'number_of_msec_of_last_update:', number_of_msec_of_last_update
#print 'total_duration:', total_duration

share_of_total_number = [0.0132743363, 0.033386967, 0.0056315366,
                         0.0148833467, 0.0096540628, 0.0225261464,
                         0.0309734513, 0.4022526146, 0.0100563154,
                         0.0148833467, 0.0225261464, 0.0116653258,
                         0.4022526146, 0.0060337892]

f1=open(sys.argv[3], 'w')

print >> f1, '# Linked Data Benchmark Council'
print >> f1, '# Social Network Benchmark'
print >> f1, '# Interactive Workload'
print >> f1
print >> f1, '# *** driver properties ***'
print >> f1, 'status=2'
print >> f1, '#threadcount=1'
print >> f1, 'timeunit=MILLISECONDS'
print >> f1
print >> f1, '# *** workload-related driver properties ***'
print >> f1, 'operationcount=' + sys.argv[2]
print >> f1, 'workload=com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload'
print >> f1, 'resultfile=test_ldbc_socnet_interactive_results.json'
print >> f1
print >> f1, '# *** workload-specific properties ***'
print >> f1
print >> f1, '# Directory containing query parameter files'
print >> f1, '# parameters_dir='
print >> f1, '# data_dir='
print >> f1
print >> f1
print >> f1, '# Unit is milliseconds'
for i in range(0, 14):
    print >> f1, 'LdbcQuery' + str(i+1) + '_interleave=' + str(int(round(total_duration / (current_read_operation * share_of_total_number[i]))))
print >> f1
print >> f1, '# *** For debugging purposes ***'
print >> f1
for i in range(0, 14):
    print >> f1, 'LdbcQuery' + str(i+1) + '_enable=true'
print >> f1, 'LdbcUpdate1AddPerson_enable=true'
print >> f1, 'LdbcUpdate2AddPostLike_enable=true'
print >> f1, 'LdbcUpdate3AddCommentLike_enable=true'
print >> f1, 'LdbcUpdate4AddForum_enable=true'
print >> f1, 'LdbcUpdate5AddForumMembership_enable=true'
print >> f1, 'LdbcUpdate6AddPost_enable=true'
print >> f1, 'LdbcUpdate7AddComment_enable=true'
print >> f1, 'LdbcUpdate8AddFriendship_enable=true'
print >> f1
print >> f1, '# *** vendor-related driver properties ***'
print >> f1
print >> f1, '# database='

f1.close()
