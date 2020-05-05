/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.util.Locale;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Leon
 */
public abstract class DBElement {

    public DBElement() {
    }
    
    public String int2StringRequest(int i) {
        if (i == -1) {
            return "NULL";
        } else {
            return String.format("%d", i);
        }
    }

    public String float2StringRequest(float f) {
        if (f == -1) {
            return "NULL";
        } else {
            return String.format(Locale.ROOT,"%f", f);
        }
    }
    
    public int StringToIntParse(String str) {
        try {
            return Integer.parseInt(str);
        }catch(NumberFormatException e){
            return  -1;
        }
    }
    
    

    public float StringToFloatParse(String str) {
        try {
            return Float.parseFloat(str);
        }catch(NumberFormatException e){
            return  -1;
        }
    }

    public abstract String getRequest();

    @Override
    public abstract String toString();
    
    
}
