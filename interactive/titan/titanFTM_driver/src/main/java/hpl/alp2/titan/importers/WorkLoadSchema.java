package hpl.alp2.titan.importers;

import java.util.Map;
import java.util.Set;

/**
 * Created by Tomer Sagi on 21-Sep-14.
 * Lists the available schemas and provides the
 * appropriate constants to implement them in the database.
 */
public interface WorkLoadSchema {
    /**
     *
     * @return Map between vertex labels in this workload and their suffix
     */
    public Map<String, Integer> getVertexTypes();

    /**
     *
     * @return array of edge labels in this workload
     */
    public Set<String> getEdgeTypes();

    /**
     *
     * @return Map between a vertex label and the properties it may posses
     */
    public Map<String,Set<String>> getVertexProperties();

    /**
     *
     * @return Map between an edge label  and the properties it may posses
     */
    public Map<String,Set<String>> getEdgeProperties();

    /**
     * Given a vertex property returns the property class
     * @param vertexType Vertex label
     * @param propertyName Property name
     * @return java class of this property
     */
    public Class<?> getVPropertyClass(String vertexType, String propertyName);

    /**
     * Given an edge property, returns the property class
     * @param edgeType Edge label
     * @param propertyName Property name
     * @return java class of this property
     */
    public Class<?> getEPropertyClass(String edgeType, String propertyName);

    /**
     * Returns a map between edge types and their files.
     * Edge types are given as triples FVL.EL.TVL (From Vertex Label, Edge Label, To Vertex Label)
     * @return Map between Edge types and the file containing them.
     */
    public Map<String,String> getEFileMap();

    /**
     * Returns a map between Vertex properties and their files
     * Vertex Properties are given as a tuples VL.PN (Vertex Label, Property Name)
     * @return Map between Vertex Properties and the file containing them
     */
    public Map<String,String> getVPFileMap();
}
