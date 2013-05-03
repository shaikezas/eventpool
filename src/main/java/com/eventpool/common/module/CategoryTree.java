package com.eventpool.common.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventpool.common.repositories.CategoryRepository;

@Service
public class CategoryTree {

	private static final Log logger = LogFactory.getLog(CategoryTree.class);
	
	@Resource
	CategoryRepository categoryRepository;

	private CategoryNode root;
	private HashMap<Long, CategoryNode> index;
	
	
	@Value("$EVENT_POOL{createCategoryTree:false}")
	private Boolean createCategoryTree; 

	@PostConstruct
	public void initCatalogueTree() {
		root = new CategoryNode(0L, "Root");
		index = new HashMap<Long, CategoryNode>(2500);
		index.put(0L, root);
		if(createCategoryTree){
			createCatalogueTreeFromDB();
		}
	}

	public boolean createCatalogueTreeFromDB() {
		try {
	    	try {
	            List<Object[]> categories = categoryRepository.getCategories();
	            int count = 0;
	            for(Object[] object:categories) {
					try {
						
							Long categoryId  = null;
							if(object[1]!=null) {
								categoryId = Long.parseLong(object[2].toString());
							}
							String categoryName = null;
							if(object[2]!=null) {
								categoryName = object[1].toString();
							}
							String parentCategoryName = null;
							if(object[4]!=null) {
								parentCategoryName = object[3].toString();
							}
							Long parentCategoryId = null;
							if(object[3]!=null) {
								parentCategoryId = Long.parseLong(object[4].toString());
							}
							if(categoryId != null) {//Some can be null due to inactive of CPTCMTRFNUM, as it is LEFT JOIN it will still return null
								count++;
								Long parentId = null;
								if(parentCategoryId != null) {
									parentId = parentCategoryId;
								}else {
									parentId = 0L;
									parentCategoryName = "Root";
								}
								addNode(categoryId, categoryName, parentId, parentCategoryName);
							}
							
					} catch (Exception e) {
						logger.error("Catalogue Tree: Exception in adding record to Catalogue Tree ",e);
					}
				}
				logger.info("Total Nodes in the category = " + count);
				logger.info("Catalogue Tree: Updating levels");
				updateLevels(root, 0);
				logger.info("Catalogue Tree: Create done");
	        } catch (HibernateException e) {
	            throw new Exception(e);
	        }

		} catch (Exception e) {
			logger.error("Catalogue Tree: Catalogue tree not created. ",e);
		}
		return true;
	}

	private void updateLevels(CategoryNode node, int curentLevel) {
		if (node == null) {
			return;
		}
		node.setLevel(curentLevel);
		for (CategoryNode child : node.getChildCategories()) {
			updateLevels(child, curentLevel + 1);
		}

	}

	private void addNode(Long categoryId, String categoryName, Long parentCategoryId, String parentCategoryName) {
		CategoryNode parentNode = null;
		CategoryNode currentNode = null;

		if (index.containsKey(categoryId)) {
			currentNode = index.get(categoryId);
			currentNode.setName(categoryName);
		} else {
			currentNode = new CategoryNode(categoryId, categoryName);
			index.put(categoryId, currentNode);
		}

		if (index.containsKey(parentCategoryId)) {
			parentNode = index.get(parentCategoryId);
		} else {
			parentNode = new CategoryNode(parentCategoryId, parentCategoryName);
			index.put(parentCategoryId, parentNode);
		}

		parentNode.addChild(currentNode);
		currentNode.setParent(parentNode);
	}

	public CategoryNode getNode(Long id) {
		CategoryNode node = null;
		if (index.containsKey(id)) {
			node = index.get(id);
		}
		return node;
	}

	public boolean isRootNode(Long id) {
		return getLevel(id) == 0;
	}

	public boolean isMetaCategoryNode(Long id) {
		return getLevel(id) == 1;
	}

	public int getLevel(Long id) {
		CategoryNode node = getNode(id);
		if (node != null) {
			return node.getLevel();
		}
		return -1;
	}

	public Long[] getAllCategoryIds() {
		Long[] catids = new Long[index.size()];
		int i = 0;
		for (Long cid : index.keySet()) {
			catids[i++] = cid;
		}
		return catids;
	}

	public List<CategoryNode> getPathToCategory(Long id) {
		List<CategoryNode> path = new ArrayList<CategoryNode>();
		CategoryNode node = getNode(id);
		if (node != null) {
			Stack<CategoryNode> s = new Stack<CategoryNode>();
			while (node != null && node.getId() != null && node.getId()!=0) { // avoid root node
				s.push(node);
				node = node.getParent();
			}
			while (!s.empty()) {
				path.add(s.pop());
			}
		}
		return path;
	}

	public Long getRootCategory(String categoryId) {
		try {
			Long catId = Long.parseLong(categoryId);
			return getRootCategory(catId);
		} catch (Exception e) {
			return null;
		}
	}

	public Long getRootCategory(Long categoryId) {

		CategoryNode node = getNode(categoryId);
		CategoryNode prevNode = node;
		if (node != null) {
			while (node != null && node.getId() != null && node.getId()!=0) {
				logger.info("Node : "+node.getName()+" - "+node.getId());
				prevNode = node;
				node = node.getParent();
			}
		}
		if (prevNode != null)
			return prevNode.getId();
		return null;
	}

	/**
	 * 
	 * @param CategoryPath ex: "Computer and Peripherals >> Computer Peripherals >> Keyboards & Mouse"
	 * @return
	 */
	public Long getLeafCategoryId(String CategoryPath)
	{
		String[] categoryNames = CategoryPath.split(">>");
		Long prevCatId = 0L;
		for(String category: categoryNames)
		{
			prevCatId  = getChildCategoryByName(prevCatId, category);
			if(prevCatId == null)
				return null;
		}
		
		return (prevCatId == 0)?null: prevCatId;
	}
	
	public Long getChildCategoryByName(Long catId, String childCatName) {
		CategoryNode node = getNode(catId);
		if (node != null) {
			try {
				childCatName = childCatName.trim();
				List<CategoryNode> childNodes = node.getChildCategories();
				for (CategoryNode cNode : childNodes) {
					if (cNode.getName().trim().equalsIgnoreCase(childCatName)) {
						return cNode.getId();
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	public CategoryNode getRoot(){
		return this.root;
	 } 
}
