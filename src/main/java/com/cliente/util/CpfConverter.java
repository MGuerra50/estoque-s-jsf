package com.cliente.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cpfConverter")
public class CpfConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return value.replaceAll("\\D", "");
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String cpf = (String) value;
        if(cpf != null && cpf.length() == 11){
            return cpf.substring(0, 3)+"."+cpf.substring(3,6)+"-"+cpf.substring(6,9)+"-"+cpf.substring(9,11);
        }
        return cpf;
    }
}
