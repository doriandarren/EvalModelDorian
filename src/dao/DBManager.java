package dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * 
 * @author campino
 *
 */
public class DBManager { 
	
	private static final String PERSISTENCE_UNIT_NAME = "EvalModelDorian"; 
	private static EntityManagerFactory factory=null;
	
	EntityManager entitymanager; 
	
	public static EntityManagerFactory getEMF(){ 
		factory = factory==null? Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
				: factory;
		return factory; 
	}
	
	

	public EntityManager getEntityManager() {
		return entitymanager;
	}

	public void connect() {
		entitymanager = getEMF().createEntityManager();
	}

	public void insert(Object object){			  
		entitymanager.getTransaction().begin();
			entitymanager.persist(object);
		entitymanager.getTransaction().commit(); 
	}


	public void update(Object object) {
		entitymanager.getTransaction().begin();
		entitymanager.persist(object);
		entitymanager.getTransaction().commit();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T find(Class<T> clazz , int id)  {
		entitymanager.getTransaction().begin();
		T object=entitymanager.find(clazz, id);
		entitymanager.getTransaction().commit();
		return object;
	}


	
	public <T> void deleteAll(Class<T> clazz){
		entitymanager.getTransaction().begin();
		ArrayList<T> list = selectAll(clazz);
		
			for(T object: list)
				entitymanager.remove(object); 
		entitymanager.getTransaction().commit();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> selectAll(Class<T> clazz){
		Query query = entitymanager.createQuery("SELECT c FROM "+ clazz.getSimpleName() +" c");		  
		 return new ArrayList<T>(query.getResultList()); 
	}

	
	/**
	 * SELECT c FROM  Comments c WHERE c.user = 'pepe'   
	 * SELECT c FROM  Comments c WHERE c.emeil = 'pepe@pepe.com'  
	 * SELECT c FROM  Comments c WHERE c.id = 12  
	 *  
	 * 
	 * @param clazz
	 * @param column
	 * @param equal
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> selectEqual(Class<T> clazz,String column,String equal){
		  Query query = entitymanager.createQuery(
				    "SELECT c FROM "+clazz.getSimpleName()+" c "
				  	+ "WHERE c."+column+"='"+equal+"'");
		  return new ArrayList<T>(query.getResultList()); 
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> selectLike(Class<T> clazz,String column,String like){
		  Query query = entitymanager.createQuery(
				    "SELECT c FROM "+clazz.getSimpleName()+" c "
				  	+ "WHERE c."+column+" LIKE '"+like+"%'");
	      
		  return new ArrayList<T>(query.getResultList()); 
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> selectBt(Class<T> clazz,String column,String bt){
		  Query query = entitymanager.createQuery(
				    "SELECT c FROM "+clazz.getSimpleName()+" c "
				  	+ "WHERE c."+column+" "+bt);	    
		  return new ArrayList<T>(query.getResultList()); 
	}
	  
	public void close() {
		entitymanager.close();
		entitymanager = null; 
	}

}
