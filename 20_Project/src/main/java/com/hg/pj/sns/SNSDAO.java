package com.hg.pj.sns;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.pj.member.Member;

@Service
public class SNSDAO {

	private int allMsgCount;
	
	@Autowired
	private SqlSession ss;
	
	@Autowired
	private SiteOption so;	// 한 페이지에 몇개 씩 보여줄건지
	
	
	public int getAllMsgCount() {
		return allMsgCount;
	}
	
	public void setAllMsgCount(int allMsgCount) {
		this.allMsgCount = allMsgCount;
	}
	
	public void calcAllMsgCount() {
		SNSSelector sSel = new SNSSelector("", null, null);
		allMsgCount = ss.getMapper(SNSMapper.class).getMsgCount(sSel);
	}

	public void getMsg(int pageNo, HttpServletRequest req) {
		
		int count = so.getSnsCountPerpage();
		int start = (pageNo - 1) * count + 1;
		int end = start + (count - 1);

		SNSSelector search = (SNSSelector) req.getSession().getAttribute("search");
		int msgCount = 0;

		if (search == null) {
			search = new SNSSelector("", new BigDecimal(start), new BigDecimal(end));
			msgCount = allMsgCount;
		} else {
			search.setStart(new BigDecimal(start));
			search.setEnd(new BigDecimal(end));
			msgCount = ss.getMapper(SNSMapper.class).getMsgCount(search);
		}

		List<SNSMsg> msgs = ss.getMapper(SNSMapper.class).getMsg(search);
		
		for (SNSMsg snsMsg : msgs) {
			snsMsg.setS_replys(ss.getMapper(SNSMapper.class).getReply(snsMsg));
		}

		int pageCount = (int) Math.ceil(msgCount / (double) count);
		req.setAttribute("pageCount", pageCount);

		req.setAttribute("msgs", msgs);
		req.setAttribute("curPage", pageNo);
		
	}

	public void writeMsg(SNSMsg sm, HttpServletRequest req) {
		String token = req.getParameter("token");
		String successToken = (String) req.getSession().getAttribute("successToken");
		// 맨 처음 글 등록 - 189

		if (token.equals(successToken)) {
			return;
		}

		Member m = (Member) req.getSession().getAttribute("loginMember");
		sm.setS_owner(m.getM_id());

		String s_txt = sm.getS_txt();
		sm.setS_txt(s_txt.replace("\r\n", "<br>"));
		
		if (ss.getMapper(SNSMapper.class).writeMsg(sm) == 1) {
			req.setAttribute("result", "글쓰기 성공");
			req.getSession().setAttribute("successToken", token);
			// 처음 쓰면 189
			allMsgCount++;
		} else {
			req.setAttribute("result", "글쓰기 실패");
		}
		
	}

	public void deleteMsg(SNSMsg sm, HttpServletRequest req) {
	
			if (ss.getMapper(SNSMapper.class).deleteMsg(sm) == 1) {
				req.setAttribute("result", "글삭제성공");
				allMsgCount--;
			} else {
				req.setAttribute("result", "글삭제실패");
			}
			req.setAttribute("result", "글삭제실패");
		
		
	}

	public void updateMsg(SNSMsg sm, HttpServletRequest req) {
			if (ss.getMapper(SNSMapper.class).updateMsg(sm) == 1) {
				req.setAttribute("result", "글수정성공");
			} else {
				req.setAttribute("result", "글수정실패");
			}
			req.setAttribute("result", "글수정실패");
	}

	public void deleteReply(SNSReply sr, HttpServletRequest req) {
			if (ss.getMapper(SNSMapper.class).deleteReply(sr) == 1) {
				req.setAttribute("result", "댓글삭제성공");
			} else {
				req.setAttribute("result", "댓글삭제실패");
			}
			req.setAttribute("result", "댓글삭제실패");
	}

	public void writeReply(SNSReply sr, HttpServletRequest req) {
		
			String token = req.getParameter("token");
			String successToken = (String) req.getSession().getAttribute("successToken");

			if (successToken != null && token.equals(successToken)) {
				return;
			}

			Member m = (Member) req.getSession().getAttribute("loginMember");
			sr.setR_owner(m.getM_id());

			if (ss.getMapper(SNSMapper.class).writeReply(sr) == 1) {
				req.setAttribute("result", "댓글쓰기성공");
				req.getSession().setAttribute("successToken", token);
			} else {
				req.setAttribute("result", "댓글쓰기실패");
			}
			req.setAttribute("result", "댓글쓰기실패");
		
	}

	public void searchMsg(SNSSelector sSel, HttpServletRequest req) {
		req.getSession().setAttribute("search", sSel);
	}

}
