package com.hg.pj.sns;

import java.util.List;

public interface SNSMapper {
	int getMsgCount(SNSSelector sSel);
	public abstract List<SNSMsg> getMsg(SNSSelector sSel);
	int writeMsg(SNSMsg sm);
	int deleteMsg(SNSMsg sm);
	int updateMsg(SNSMsg sm);
	List<SNSReply> getReply(SNSMsg snsMsg);
	int deleteReply(SNSReply sr);
	int writeReply(SNSReply sr);

}
