package Controllers;

import Models.Palabrasclaves;
import Controllers.util.JsfUtil;
import Controllers.util.JsfUtil.PersistAction;
import Models.PalabrasclavesFacade;

import java.io.Serializable;
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

@Named("palabrasclavesController")
@SessionScoped
public class PalabrasclavesController implements Serializable {

    @EJB
    private Models.PalabrasclavesFacade ejbFacade;
    private List<Palabrasclaves> items = null;
    private Palabrasclaves selected;

    public PalabrasclavesController() {
    }

    public Palabrasclaves getSelected() {
        return selected;
    }

    public void setSelected(Palabrasclaves selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PalabrasclavesFacade getFacade() {
        return ejbFacade;
    }

    public Palabrasclaves prepareCreate() {
        selected = new Palabrasclaves();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Language/Bundle").getString("PalabrasclavesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Language/Bundle").getString("PalabrasclavesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Language/Bundle").getString("PalabrasclavesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Palabrasclaves> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public Palabrasclaves getPalabrasclaves(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Palabrasclaves> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Palabrasclaves> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Palabrasclaves.class)
    public static class PalabrasclavesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalabrasclavesController controller = (PalabrasclavesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palabrasclavesController");
            return controller.getPalabrasclaves(getKey(value));
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
            if (object instanceof Palabrasclaves) {
                Palabrasclaves o = (Palabrasclaves) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Palabrasclaves.class.getName()});
                return null;
            }
        }

    }

}
