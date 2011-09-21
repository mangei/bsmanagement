/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.geblauf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jgoodies.binding.beans.Model;

import cw.roommanagementmodul.persistence.Bewohner;

/**
 *
 * @author Dominik
 */
public class BewohnerTarifSelection extends Model{

    private HashMap<Bewohner, List<GebTarifSelection>> bewohnerTarifMap;

    public BewohnerTarifSelection() {

        bewohnerTarifMap = new HashMap<Bewohner, List<GebTarifSelection>>();

    }


    public Map getMap(){
        return bewohnerTarifMap;
    }

    public void put(Bewohner b,List<GebTarifSelection> gebTarifList){
        bewohnerTarifMap.put(b, gebTarifList);
    }

    public List<GebTarifSelection> get(Bewohner b){
        if(bewohnerTarifMap!=null){
            return bewohnerTarifMap.get(b);
        }else{
            return null;
        }
    }

}
