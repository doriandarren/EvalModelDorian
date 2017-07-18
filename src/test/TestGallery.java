package test;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;
import model.Item;

public class TestGallery {

	DBAdmin dbConnector; 

	@Before
	public void init(){
		dbConnector =  new DBAdmin(); 	
		dbConnector.connect();
		dbConnector.deleteAll(Admin.class);
		dbConnector.close();
	}
	
	//@Test(expected=RuntimeException.class)
	public void testcreateGalleryAdminNull() {
		/**
		 * Lanza una exception
		 */
		Admin admin = null; 
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		dbConnector.createGallery(admin, gallery);		
	}

	//@Test(expected=RuntimeException.class)
	public void testCreateGalleryNull() {
		Admin admin = getMockAdmin("Administrador");
		Gallery gallery = null;
		
		dbConnector.createGallery(admin, gallery);		
		
	}
	
	@Test
	public void testCreateGallery(){
		Admin admin = getMockAdmin("Administrador");
		
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
			dbConnector.getEntityManager().persist(admin);	
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();
		
			
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		
		dbConnector.createGallery(admin, gallery);
		
		dbConnector.connect();
			Admin adminRecovered = dbConnector.find(Admin.class, admin.getId());
		dbConnector.close();
		
		Assert.assertNotNull(adminRecovered);
		Assert.assertEquals(true,adminRecovered.getGalleries().iterator().next().getId()>0);
		Assert.assertEquals("Galeria 1",adminRecovered.getGalleries().iterator().next().getName());
	}
	
	
	//@Test
	public void testRemoveGallery() {
		Admin admin = getMockAdmin("Administrador");
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
			dbConnector.getEntityManager().persist(admin);
			gallery.setAdmin(admin);
			admin.getGalleries().add(gallery);			
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();

		
		
		dbConnector.removeGallery(gallery);

		dbConnector.connect();
		Admin adminRecovered = dbConnector.find(Admin.class, admin.getId());
		Gallery galleryRecovered = dbConnector.find(Gallery.class, gallery.getId());
		dbConnector.close();

		Assert.assertNull(adminRecovered);
		Assert.assertNull(galleryRecovered);
	}
		
	
	//@Test
	public void testUpdateGallery() {		
		
		Admin admin = getMockAdmin("Administrador");
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
			dbConnector.getEntityManager().persist(admin);
			gallery.setAdmin(admin);
			admin.getGalleries().add(gallery);			
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();
		
		
		admin.getGalleries().iterator().next().setDescription("Descripcion Update Galeria 1");
		
		dbConnector.update(gallery);
		
		dbConnector.connect();
		Admin adminRecovered = dbConnector.find(Admin.class, admin.getId());
		dbConnector.close();
		
		Assert.assertNotNull(adminRecovered);
		Assert.assertEquals(true,adminRecovered.getGalleries().iterator().next().getId()>0);
		Assert.assertEquals("Descripcion Update Galeria 1",adminRecovered.getGalleries().iterator().next().getDescription());
	
	}
	
	
	
	
	//@Test
	public void testGetItems() {	
		Admin admin = getMockAdmin("Administrador");
		
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		
		Item item1 = getMosckItem("New Item 1", "Descripcion New Item 1", 5.63f);
		Item item2 = getMosckItem("New Item 2", "Descripcion New Item 2", 5.50f);
		
		dbConnector.connect();
			dbConnector.getEntityManager().getTransaction().begin();
			dbConnector.getEntityManager().persist(admin);
			
			gallery.setAdmin(admin);
			admin.getGalleries().add(gallery);
			
			gallery.getItems().add(item1);
			gallery.getItems().add(item2);
					
			
			dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();
		
		
		dbConnector.connect();
			Admin adminRecovered = dbConnector.find(Admin.class, admin.getId());
		dbConnector.close();
		
		
		Set<Item> itemsList = dbConnector.getItems(adminRecovered.getGalleries().iterator().next().getId());
		
		Assert.assertEquals(2, itemsList.size());
		
		Assert.assertNotNull(findGallery(itemsList, "New Item 1"));
		Assert.assertNotNull(findGallery(itemsList, "New Item 2"));
		
		
	}
	
	
	private static Item findGallery(Set<Item> set, String nameItem){ 
		for(Item item: set){
				if(item.getName().equals(nameItem))
				  return item; 
		}
		return null;
	}
	
	
	public Admin getMockAdmin(String name){
		Admin admin = new Admin();
		admin.setName(name);
		return admin;
	}
	
	public Gallery getMockGallery(String name, String galeria){
		Gallery gallery = new Gallery();
		gallery.setName(name);
		gallery.setDescription(galeria);
		return gallery;
	}
	
	public Item getMosckItem(String name, String description, float price){
		Item item = new Item();
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);
		return item;
	}
	
}
