package edu.buaa.sem.common;

/**
 * Dao类的接口，每一个Dao类必修实现该接口
 * 
 */
public interface IDao<T> {

	/**
	 * 批量更新方法
	 * 
	 */
	public void updateByKeys(T pojo, String key, long[] ids);
}