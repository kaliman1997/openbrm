/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.banwire;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class BanWireTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println(new PagoPro().sendRequest());
        Map<String, Object> params = new LinkedHashMap<>();
        /*
        params.put("method", "add");
        params.put("user", "pruebasbw");
        params.put("number", "5134422031476272");
        params.put("exp_month", "12");
        params.put("exp_year", "19");
        params.put("cvv", "162");
        params.put("name", "Pruebasbw");
        params.put("address", "Horacio");
        params.put("postal_code", "11560");
        params.put("phone", "15799155");
        params.put("email", "jruiz@banwire.com");        
        
        System.out.println(new PagoOnDemand().sendRequest("card&exists=1", params)); */
        
        params.put("method", "payment");
        params.put("user", "pruebasbw");
        params.put("reference", "54321");
        params.put("token", "crd.1jJckdSrFY3uAVZecM1voax1gLhv");
        params.put("amount", ".50");
        
        System.out.println(new PagoOnDemand().sendRequest("card", params));
    }
    
}
