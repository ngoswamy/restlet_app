package com.pgi.imeet.vcard.model;
/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:xs="http://www.w3.org/2001/XMLSchema" name="vcard">
 *   &lt;xs:complexType>
 *     &lt;xs:simpleContent>
 *       &lt;xs:extension base="xs:string">
 *         &lt;xs:attribute type="xs:string" use="optional" name="attributeName"/>
 *         &lt;xs:attribute type="xs:string" use="optional" name="value"/>
 *       &lt;/xs:extension>
 *     &lt;/xs:simpleContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Vcard
{
    private String string;
    private String attributeName;
    private String value;

    /** 
     * Get the extension value.
     * 
     * @return value
     */
    public String getString() {
        return string;
    }

    /** 
     * Set the extension value.
     * 
     * @param string
     */
    public void setString(String string) {
        this.string = string;
    }

    /** 
     * Get the 'attributeName' attribute value.
     * 
     * @return value
     */
    public String getAttributeName() {
        return attributeName;
    }

    /** 
     * Set the 'attributeName' attribute value.
     * 
     * @param attributeName
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /** 
     * Get the 'value' attribute value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'value' attribute value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
