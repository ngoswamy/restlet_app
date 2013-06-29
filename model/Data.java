package com.pgi.imeet.vcard.model;
import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="data">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="vcard" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Data
{
    private List<Vcard> dataList = new ArrayList<Vcard>();

    /** 
     * Get the list of 'vcard' element items.
     * 
     * @return list
     */
    public List<Vcard> getDatas() {
        return dataList;
    }

    /** 
     * Set the list of 'vcard' element items.
     * 
     * @param list
     */
    public void setDatas(List<Vcard> list) {
        dataList = list;
    }
}
