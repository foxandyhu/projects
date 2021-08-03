/**
 * 抽象接口,所有的service需要实现该接口
 */
export interface IBaseService {

  /**
   * 数据列表
   */
  getPager(params: Map<string, string>): Promise<any>;

  /**
   * 删除数据
   */
  delData(ids: Array<number>): Promise<any>;

  /**
   * 保存数据
   * @param data
   */
  saveData(data: any): Promise<boolean>;

  /**
   * 编辑数据
   * @param data
   */
  editData(data: any): Promise<boolean>;

  /**
   * 根据ID曹肇数据
   * @param id
   */
  getData(id: any): Promise<any>;

  /**
   * 排序
   * @param upItemId
   * @param downItemId
   */
  sort(upItemId, downItemId): Promise<boolean>;
}
