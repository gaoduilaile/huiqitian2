package yunju.com.huiqitian.entity;

import yunju.com.huiqitian.vm.widget.IRollItem;

public class RollItem implements IRollItem {
	String imageUrl;
	/*String title;*/

	/* (non-Javadoc)
	 * @see cn.itcast.wh12.arl.IRollItem#getImageUrl()
	 */
	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	/* (non-Javadoc)
	 * @see cn.itcast.wh12.arl.IRollItem#getTitle()
	 */
	/*@Override
	public String getTitle() {
		return title;
	}*/

	public RollItem(String imageUrl) {
		super();
		this.imageUrl = imageUrl;
		/*this.title = title;*/
	}

}
