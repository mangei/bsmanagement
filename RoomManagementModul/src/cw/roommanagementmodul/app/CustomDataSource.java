/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.app;

import cw.roommanagementmodul.geblauf.GebTarifSelection;
import cw.roommanagementmodul.pojo.Bewohner;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Dominik
 */
public class CustomDataSource implements JRDataSource{

    private List<Bewohner> bewohnerList;
    private Map<Bewohner,List<GebTarifSelection>> tarifMap;

    private List<GebTarifSelection> tarifList;

    private int bewohnerAnz=0;
    private int index=0;

    public CustomDataSource(List<Bewohner> bewohnerList, Map<Bewohner, List<GebTarifSelection>> tarifMap){
        this.bewohnerList=bewohnerList;
        this.tarifMap=tarifMap;

    }

    public boolean next() throws JRException {
        if(bewohnerAnz<bewohnerList.size()){
           tarifList=tarifMap.get(bewohnerList.get(bewohnerAnz)); 
        }else{
            return false;
        }
        if(index<tarifList.size()){
            index++;
            return true;
        }else{
            bewohnerAnz++;
            return false;
        }
    }

    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;

		String fieldName = field.getName();

		if ("tarif".equals(fieldName))
		{
			value = tarifList.get(index).getTarif().getTarif();
		}
		else if ("gebuehr".equals(fieldName))
		{
			value = tarifList.get(index).getGebuehr().getGebuehr().getName();
		}
		else if ("gebKat".equals(fieldName))
		{
			value = tarifList.get(index).getGebuehr().getGebuehr().getGebKat().getName();
		}
		else if ("anmerkung".equals(fieldName))
		{
			value = tarifList.get(index).getGebuehr().getAnmerkung();
		}

		return value;
    }




}
