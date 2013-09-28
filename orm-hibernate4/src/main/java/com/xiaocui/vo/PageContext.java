package com.xiaocui.vo;

/**
 * 用来传递对象的ThreadLocal数据
 * 
 * @author Xiaocui
 * 
 */
public class PageContext {
	/**
	 * 分页起始页
	 */
	private static ThreadLocal<Integer> offset = new ThreadLocal<Integer>();
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> size = new ThreadLocal<Integer>();
	/**
	 * 分页列表排序字段
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	/**
	 * 分页列表排序方式 desc asc
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();

	private static ThreadLocal<String> realPath = new ThreadLocal<String>();

	/**
	 * 获取分页起始页
	 * 
	 * @return
	 */
	public static Integer getOffset() {
		return offset.get();
	}

	public static void setOffset(Integer _offset) {
		offset.set(_offset);
	}

	public static void removeOffset() {
		offset.remove();
	}

	/**
	 * 获取分页大小
	 * 
	 * @return
	 */
	public static Integer getSize() {
		return size.get();
	}

	public static void setSize(Integer _size) {
		size.set(_size);
	}

	public static void removeSize() {
		size.remove();
	}

	/**
	 * 获取排序字段
	 * 
	 * @return
	 */
	public static String getSort() {
		return sort.get();
	}

	public static void setSort(String _sort) {
		sort.set(_sort);
	}

	public static void removeSort() {
		sort.remove();
	}

	/**
	 * 获取排序方式
	 * 
	 * @return
	 */
	public static String getOrder() {
		return order.get();
	}

	public static void setOrder(String _order) {
		order.set(_order);
	}

	public static void removeOrder() {
		order.remove();
	}

	public static String getRealPath() {
		return realPath.get();
	}

	public static void setRealPath(String _realPath) {
		realPath.set(_realPath);
	}

	public static void removeRealPath() {
		realPath.remove();
	}
}
