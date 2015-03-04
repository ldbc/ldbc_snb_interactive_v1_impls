package hpl.alp2.titan.importers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomer Sagi on 21-Sep-14.
 * Lists available workloads and provides information about them
 */
public enum WorkloadEnum {INTERACTIVE(new InteractiveWorkloadSchema()),ANALYTICS(new InteractiveWorkloadSchema()),BASICGRAPH(new InteractiveWorkloadSchema());
    private WorkLoadSchema s;

    WorkloadEnum(WorkLoadSchema s) { this.s = s; }

    public WorkLoadSchema getSchema() {
        return s;
    }
}
