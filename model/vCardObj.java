/**
 * Mar 13, 2009
 * @author Neeraj Goswamy
 */
package com.pgi.imeet.vcard.model;

import java.io.Serializable;
import java.util.Date;

public class vCardObj implements Serializable
{
    public int vcardId = 0;
    public String vCard;
    public String cardData;

    public vCardObj()
    {
    }

    
    /**
	 * @return the cardData
	 */
	public String getCardData() {
		return cardData;
	}


	/**
	 * @param cardData the cardData to set
	 */
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}


	public String getVCard(){
		return this.vCard;
	}
	public void putVCard(String vCard){

		this.vCard= vCard;
	}
}
