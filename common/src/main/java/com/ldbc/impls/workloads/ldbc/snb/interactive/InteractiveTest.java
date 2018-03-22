package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.SnbDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

public abstract class InteractiveTest extends SnbTest {

    public InteractiveTest(SnbDb db) throws DbException {
        super(db, new LdbcSnbInteractiveWorkload());
    }

    @Test
    public void testQueries() throws DbException, IOException {
        run(db, new LdbcQuery1(30786325579101L, "Ian", LIMIT));
        run(db, new LdbcQuery2(19791209300143L, new Date(1354060800000L), LIMIT));
        run(db, new LdbcQuery3(15393162790207L, "Puerto_Rico", "Republic_of_Macedonia", new Date(1291161600000L), 30, LIMIT));
        run(db, new LdbcQuery4(10995116278874L, new Date(1338508800000L), 28, LIMIT));
        run(db, new LdbcQuery5(15393162790207L, new Date(1344643200000L), LIMIT));
        run(db, new LdbcQuery6(30786325579101L, "Shakira", LIMIT));
        run(db, new LdbcQuery7(26388279067534L, LIMIT));
        run(db, new LdbcQuery8(2199023256816L, LIMIT));
        run(db, new LdbcQuery9(32985348834013L, new Date(1346112000000L), LIMIT));
        run(db, new LdbcQuery10(30786325579101L, 7, LIMIT));
        run(db, new LdbcQuery11(30786325579101L, "Puerto_Rico", 2004, LIMIT));
        run(db, new LdbcQuery12(19791209300143L, "BasketballPlayer", LIMIT));
        run(db, new LdbcQuery13(32985348833679L, 26388279067108L));
        run(db, new LdbcQuery14(32985348833679L, 2199023256862L));
        db.close();
        workload.close();
    }

}

