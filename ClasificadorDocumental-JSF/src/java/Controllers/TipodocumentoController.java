package Controllers;

import Models.Tipodocumento;
import Controllers.util.JsfUtil;
import Controllers.util.JsfUtil.PersistAction;
import static Controllers.util.JsfUtil.addErrorMessage;
import Models.TipodocumentoFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@Named("tipodocumentoController")
@SessionScoped
public class TipodocumentoController implements Serializable {

    @EJB
    private Models.TipodocumentoFacade ejbFacade;
    private List<Tipodocumento> items = null;
    private Tipodocumento selected;

    public TipodocumentoController() {
    }

    public Tipodocumento getSelected() {
        return selected;
    }

    public void setSelected(Tipodocumento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipodocumentoFacade getFacade() {
        return ejbFacade;
    }

    public Tipodocumento prepareCreate() {
        selected = new Tipodocumento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Language/Bundle").getString("TipodocumentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Language/Bundle").getString("TipodocumentoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Language/Bundle").getString("TipodocumentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Tipodocumento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    //Creamos un métodos para obtener el path de la aplicación web.
    public static String getPath() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            return ctx.getRealPath("/");

        } catch (Exception e) {

            addErrorMessage("getPath() " + e.getLocalizedMessage());
        }
        return null;
    }

    //Creamos un métodos para obtener el path relativo al repositorio y devuelve un hashmap con la ruta de los archivos.
    public static HashMap<String, String> getMapPathRepositorioPpal() {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
           
            String path = getPath() + "resources/repositorioPpal/";
            map.put("path", path);
            map.put("url", "/resources/repositorioPpal/");
            return map;
        } catch (Exception e) {

            addErrorMessage(" getMapPathRepositorioPpal() " + e.getLocalizedMessage());
        }
        return null;
    }
    
    //Creamos un métodos para copiar los archivos.
    public static String getPathRepositorioPpal() {
        try {             
        
            String path = getPath() + "resources/repositorioPpal/";
 return path;
        } catch (Exception e) {

            addErrorMessage("getPathRepositorioPpal() " + e.getLocalizedMessage());
        }
        return null;
    }
    
    
    
    
    
    
    
    
 
    
    
    
    
    
    
    
    
    
    
    
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Language/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Language/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Tipodocumento getTipodocumento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Tipodocumento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Tipodocumento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Tipodocumento.class)
    public static class TipodocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipodocumentoController controller = (TipodocumentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipodocumentoController");
            return controller.getTipodocumento(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Tipodocumento) {
                Tipodocumento o = (Tipodocumento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tipodocumento.class.getName()});
                return null;
            }
        }

    }

}
