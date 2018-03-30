package com.bfly.industry.page;

/**
 * 分页接口
 * @author 胡礼波
 * 2012-4-26 下午09:21:36
 */
public interface Paginable {

	/**
	 * 总记录数
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:00
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 总页数
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:06
	 * @return
	 */
	public int getTotalPage();

	/**
	 * 每页记录数
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:12
	 * @return
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:18
	 * @return
	 */
	public int getPageNo();

	/**
	 *  是否第一页
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:24
	 * @return
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:30
	 * @return
	 */
	public boolean isLastPage();

	/**
	 *  返回下页的页号
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:37
	 * @return
	 */
	public int getNextPage();

	/**
	 * 返回上页的页号
	 * @author 胡礼波
	 * 2012-4-26 下午09:22:43
	 * @return
	 */
	public int getPrePage();
}
