package entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CategoryBindIngredient {
    private String idCateg, nameIng, id;
    
    public CategoryBindIngredient(){
        
    }
    
    public CategoryBindIngredient(String idCateg) {
        this.idCateg = idCateg;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getIdCateg() {
        return idCateg;
    }
    
    public void setIdCateg(String idCateg) {
        this.idCateg = idCateg;
    }
    
    public String getNameIng() {
        return nameIng;
    }
    
    public void setNameIng(String nameIng) {
        this.nameIng = nameIng;
    }
}
