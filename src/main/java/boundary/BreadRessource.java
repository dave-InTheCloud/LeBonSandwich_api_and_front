package boundary;

import entity.Bread;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Ressource des pains
 */
@Stateless
public class BreadRessource {
    /**
     * EntityManager
     */
    @PersistenceContext
            EntityManager em;
    
    /**
     * Methode permettant d'enregistrer un pain
     * @param name nom du pain a enregistrer
     * @param size taille du pain a enregistrer
     * @return pain enregistre
     */
    public Bread save(String name, String size){
        Bread b = new Bread();
        
        b.setId(UUID.randomUUID().toString());
        b.setName(name);
        b.setSize(size);
        
        return this.em.merge(b);
    }
    
    /**
     * Methode permettant d'obtenir un pain a partir de son identificateur
     * @param id identificateur du pain
     * @return pain correspondant a l'identificateur
     */
    public Bread findById(String id){
        Bread res = null;
        
        try{
            res = this.em.find(Bread.class,id);
        }catch(EntityNotFoundException e){
            
        }
        
        return res;
    }
    
    /**
     * Methode permettant d'obtenir la liste de tous les pains
     * @return liste des pains
     */
    public List<Bread> findAll(){
        Query q = this.em.createNamedQuery("Bread.findAll",Bread.class);
        q.setHint("javax.persistence.cache.storeMode",CacheStoreMode.REFRESH);
        
        return q.getResultList();
    }
    
      /**
     * Methode permettant de mettre a jour un pain
     * @param id identificateur du pain
     * @param bread les nouveaux attributs de pain
     * @return pain mis a jour
     */
    public Bread update(String id,Bread bread) throws EntityNotFoundException {
        try {
            Bread ref = this.em.getReference(Bread.class, id);
            ref.setName(bread.getName());
            ref.setSize(bread.getSize());

            return ref;
            
        } catch (Exception e) {
            System.out.println("pain non trouve");
            return null;
        }
    }
    
    /**
     * Methode permettant de supprimer un pain
     * @param id identificateur du pain a supprimer
     */
    public void delete(String id){
        try {
            Bread ins = this.em.getReference(Bread.class,id);
            this.em.remove(ins);
        } catch (EntityExistsException ex){
            System.out.println("The object doesn't exist");
        }
    }
}
