package hpl.alp2.titan.drivers.interactive;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.util.PipesFunction;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import com.tinkerpop.pipes.util.structures.Table;
import hpl.alp2.titan.importers.InteractiveWorkloadSchema;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 19-Oct-14.
 * Contains utilities for implementing LDBC queries.
 * Singleton design pattern
 */
public class QueryUtils {
    static public Calendar GMT_CAL = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    final public static PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean> LOOPTRUEFUNC = new PipesFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
        @Override
        public Boolean compute(LoopPipe.LoopBundle<Vertex> argument) {
            return true;
        }
    };
    static PipesFunction<Vertex, Boolean> ONLYPOSTS = new PipesFunction<Vertex, Boolean>() {
        @Override
        public Boolean compute(Vertex v) {
            return ((String) v.getProperty("label")).equalsIgnoreCase("Post");
        }
    };
    static private QueryUtils qu = new QueryUtils();
    private InteractiveWorkloadSchema s;

    private QueryUtils() {
        s = new InteractiveWorkloadSchema();
    }

    public static QueryUtils getInstance() {
        return qu;
    }

    /**
     * Given a vertex representing a person collects information about
     * the universities this person has gone to using a table.
     *
     * @param v Person vertex for which to collect universities
     * @return list of lists, internal list is
     * (uniCity:CITY)<-[:IS_LOCATED_IN]-(uni:UNIVERSITY)<-[studyAt:STUDY_AT]-(person)
     */
    public static List<List<Object>> collectUnis(Vertex v) {

        List<List<Object>> res = new ArrayList<>();
        Table table = new Table();
        GremlinPipeline<Vertex, List<Vertex>> gp = new GremlinPipeline<>(v);
//        List<Edge> studyEdges= (new GremlinPipeline<Vertex,List<Edge>>(v).outE("studyAt").aggregate()).toList();
//        List<Vertex> uniVertices = (new GremlinPipeline<Vertex,List<Vertex>>(v).outE("studyAt").outV().aggregate()).toList();
//        List<Vertex> uniCountries = (new GremlinPipeline<Vertex,List<Vertex>>(v).outE("studyAt").outV().out("isLocatedIn").aggregate()).toList();
        gp.outE("studyAt").as("e").inV().as("u").out("isLocatedIn").as("c").table(table).iterate();
        for (Row r : table) {
            ArrayList<Object> a = new ArrayList<>();
            a.add(((Vertex) r.get(1)).getProperty("name")); //University->name
            a.add(((Edge) r.get(0)).getProperty("classYear")); //studyAt->classYear
            a.add(((Vertex) r.get(2)).getProperty("name")); //City->name
            res.add(a);
        }
        return res;
    }

    /**
     * Given a vertex representing a person collects information about
     * the companies this person has worked at using a table.
     *
     * @param v Person for which to collect companies
     * @return list of lists, internal list is
     * (companyCountry:PLACE:COUNTRY)<-[:IS_LOCATED_IN]-(company:COMPANY)<-[worksAt:WORKS_AT]-(person)
     */
    public static List<List<Object>> collectComps(Vertex v) {
        //TODO combine with collectUnis
        List<List<Object>> res = new ArrayList<>();
        Table table = new Table();
        GremlinPipeline<Vertex, List<Vertex>> gp = new GremlinPipeline<>(v);
        gp.outE("workAt").as("e").inV().as("c").out("isLocatedIn").as("co").table(table).iterate();
        for (Row r : table) {
            ArrayList<Object> a = new ArrayList<>();
            a.add(((Vertex) r.get(1)).getProperty("name")); //Company->name
            a.add(((Edge) r.get(0)).getProperty("workFrom")); //workAt->workFrom
            a.add(((Vertex) r.get(2)).getProperty("name")); //Country->name
            res.add(a);
        }
        return res;
    }

    /**
     * Given a vertex representing a person collects information about
     * The city the person is located in (assumes only one)
     *
     * @param v person to get city info for
     * @return Vertex of city the person is located at
     * (uniCity:CITY)<-[:IS_LOCATED_IN]-(person)
     */
    public static Vertex getPersonCity(Vertex v) {

        Iterator<Vertex> it = (new GremlinPipeline<Vertex, List<Vertex>>(v)).out("isLocatedIn").iterator();
        if (it.hasNext())
            return it.next();
        else
            return null;
    }

    /**
     * Thanks http://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
     *
     * @param date date to increment
     * @param days num days to increment by
     * @return date incremented
     */
    public static long addDays(long date, int days) {
        return date + ((long) days) * 24 * 60 * 60 * 1000;
    }

    /**
     * Given a country vertex, return a set of city ids
     * @param country country vertex to reutrn cities for
     * @return set of city ids with a "isPartOf relation with this country
     */
    public static Set<Long> getCities(Vertex country) {
        GremlinPipeline<Vertex, Vertex> gp = new GremlinPipeline<>(country);
        gp.in("isPartOf");
        Set<Long> cities = new HashSet<>();
        for (Vertex v : gp) {
            cities.add((Long) v.getId());
        }
        return cities;

    }

    /**
     * Sort a given map by it's value
     *
     * @param map to sort
     * @param asc if true, sorted ascending by value
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue(Map<K, V> map, boolean asc) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());

        if (asc)
            Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                @Override
                public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
        else
            Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                @Override
                public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Sort a given map by it's value
     *
     * @param map           to sort
     * @param asc           if true, sorted ascending by value
     * @param byKeyAsc      @return secondary key sort order, if true - ascending
     * @param keyComparator Comparator to sort by key (value assumed comparable)
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValueAndKey(Map<K, V> map, boolean asc, boolean byKeyAsc, final Comparator<K> keyComparator) {

        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());

        if (asc) {
            if (byKeyAsc)
                Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                        if ((o1.getValue()).compareTo(o2.getValue()) == 0)
                            return keyComparator.compare(o1.getKey(), o2.getKey());
                        else
                            return (o1.getValue()).compareTo(o2.getValue());
                    }
                });
            else
                Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                        if ((o1.getValue()).compareTo(o2.getValue()) == 0)
                            return keyComparator.compare(o2.getKey(), o1.getKey());
                        else
                            return (o1.getValue()).compareTo(o2.getValue());
                    }
                });
        } else {
            if (byKeyAsc)
                Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                        if ((o1.getValue()).compareTo(o2.getValue()) == 0)
                            return keyComparator.compare(o1.getKey(), o2.getKey());
                        else
                            return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
            else
                Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                        if ((o1.getValue()).compareTo(o2.getValue()) == 0)
                            return keyComparator.compare(o2.getKey(), o1.getKey());
                        else
                            return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
        }

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Given a vertex returns the type label using the id suffix
     *
     * @param v    Vertex to check
     * @param mult power of 10 used to create suffix (10 for 1 digit, 100 for 2 digits, etc..)
     * @return String representing the vertex label
     */
    public String getVertexType(Vertex v, int mult) {
        Integer i = (int) ((Long) v.getId() % mult);
        return this.s.getVertexTypesReverse().get(i);
    }

    /**
     * Given a person, returns the set of friends and friends of friends
     * , excluding that person
     *
     * @param rootId personID to start from
     * @param client TitanFTMDb.BasicClient to use for root retrieval
     * @return Set<Vertex> of the persons friends and their friends
     */
    public Set<Vertex> getFoF(long rootId, TitanFTMDb.BasicClient client) {
        Set<Vertex> res = new HashSet<>();
        Vertex root = null;
        try {
            root = client.getVertex(rootId, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<Vertex, Vertex>(root));
        gp.out("knows").aggregate(res)
                .out("knows").aggregate(res).iterate();
        res.remove(root);
        return res;
    }

    static final PipeFunction<Pair<Row, Row>, Integer> COMP_CDate_Postid = new PipeFunction<Pair<Row, Row>, Integer>() {

        @Override
        public Integer compute(Pair<Row, Row> argument) {
            Vertex v1 = (Vertex) argument.getA().getColumn("post");
            Vertex v2 = (Vertex) argument.getB().getColumn("post");
            long d1 = v1.getProperty("creationDate");
            long d2 = v2.getProperty("creationDate");
            if (d1 == d2) {
                return ((Long) v1.getId()).compareTo((Long) v2.getId());
            } else
                return Long.compare(d2, d1);
        }
    };

}
