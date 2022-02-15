library(ggplot2)
library(tidyverse)
library(plyr)
library(stringr)
library(scales)

# load

results = read.csv("../cypher/results/LDBC-SNB-results_log.csv", sep="|")
results

# preprocess

results$operation_category = str_extract(results$operation_type, regex("(Query|ShortQuery|Update)"))
results$operation_category = gsub("ShortQuery", "Short", results$operation_category)
results$operation_category = gsub("Query", "Complex", results$operation_category)

# starting times

ggplot(results, aes(x=scheduled_start_time, y=execution_duration_MILLISECONDS, col=operation_type, shape=operation_category)) +
  geom_point(size=3) +
  scale_shape_manual(values=c(3, 16, 17))

ggplot(results, aes(x=scheduled_start_time, y=actual_start_time, col=operation_type, shape=operation_category)) +
  geom_point()

# results

gm_mean = function(x, na.rm=TRUE) {
  exp(sum(log(x[x > 0]), na.rm=na.rm) / length(x))
}

results$op <- as.numeric(gsub("[^0-9]*([0-9]+)[^0-9]*$", "\\1", results$operation_type))
results$op <- sprintf("%02d", results$op)

aggregated <- ddply(
  .data = results,
  .variables = c("operation_type", "op", "operation_category"),
  .fun = colwise(gm_mean)
)

ggplot(aggregated, aes(x=op, y=execution_duration_MILLISECONDS)) +
  geom_point() +
  facet_wrap(~operation_category, ncol=1, scales="free") +
  theme_bw()

ggplot(results, aes(x=op, y=execution_duration_MILLISECONDS)) +
  geom_point(alpha=0.25) +
  facet_wrap(~operation_category, ncol=1, scales="free") +
  theme_bw()

ggplot(results, aes(x=op, y=execution_duration_MILLISECONDS, fill=operation_category)) +
  geom_violin(scale="width") +
  scale_fill_brewer(palette="PuOr") +
  scale_y_log10(
    breaks=c( 0.0001,   0.001,   0.01,   0.1,   1,   10,   100,   1000,   10000,   100000),
    labels=c("0.0001", "0.001", "0.01", "0.1", "1", "10", "100", "1000", "10000", "100000")
  ) +
  facet_wrap(~operation_category, ncol=1, scales="free") +
  xlab("Operation number") +
  ylab("Execution time [ms]") +
  theme_bw()

