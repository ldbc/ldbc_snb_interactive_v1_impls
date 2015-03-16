/**
 (c) Copyright [2015] Hewlett-Packard Development Company, L.P.
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
