package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.MessageReceive;
import model.MessageSend;
import model.MessageSent;
import model.dao.ConnectionManager;
import model.dao.MessageDao;


public class MessageDAOImpl implements MessageDao {
	
	
	private ConnectionManager cm = new ConnectionManager();
	
	public static java.sql.Date getCurrentDatetime() {
	       java.util.Date today = new java.util.Date();
	       return new java.sql.Date(today.getTime());
	}

	public List<MessageSent> getMessageSentList(String user_id) {
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		String query = "SELECT msg_id, msg_content, send_time, receiver_id, sender_id "
				+ "FROM MESSAGE "
				+ "WHERE sender_id = ? ";

		
		try {
			conn = cm.getConnection();
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, user_id);
			rs = pStmt.executeQuery();
			
			List<MessageSent> list = new ArrayList<MessageSent>();
			while (rs.next()) {
				int msg_id = rs.getInt("msg_id");
				String msg_content = rs.getString("msg_content");
				Date send_time = rs.getDate("send_time");
				String receiver_id = rs.getString("receiver_id");
				String sender_id = rs.getString("sender_id");
	
				MessageSent msg_sent = new MessageSent(msg_id, msg_content, send_time, receiver_id, sender_id);
				list.add(msg_sent);	
			}
			return list;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) 
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}	
		return null;
	}

	public List<MessageReceive> getMessageReceiveList(String user_id) {
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		String query = "SELECT msg_id, msg_content, receive_time, receiver_id, sender_id "
				+ "FROM MESSAGE "
				+ "WHERE receiver_id = ? ";
				
				try { 
					conn = cm.getConnection();
					pStmt = conn.prepareStatement(query);
					pStmt.setString(1, user_id);
					rs = pStmt.executeQuery();			
					List<MessageReceive> list = new ArrayList<MessageReceive>();
					while (rs.next()) {	
						int msg_id = rs.getInt("msg_id");
						String msg_content = rs.getString("msg_content");
						Date receive_time = rs.getDate("receive_time");
						String receiver_id = rs.getString("receiver_id");
						String sender_id = rs.getString("sender_id");
			
						MessageReceive msg_rece = new MessageReceive(msg_id, msg_content, receive_time, receiver_id, sender_id);
						list.add(msg_rece);
					}
					return list;		
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (rs != null) 
						try { 
							rs.close(); 
						} catch (SQLException ex) { ex.printStackTrace(); }
					if (pStmt != null) 
						try { 
							pStmt.close(); 
						} catch (SQLException ex) { ex.printStackTrace(); }
					if (conn != null) 
						try { 
							conn.close(); 
						} catch (SQLException ex) { ex.printStackTrace(); }
				}		
				return null;	
	}
	
	public MessageSend insertMessageSend(MessageSend msg_send) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		String query = "INSERT INTO MESSAGE (MSG_ID, MSG_CONTENT, SEND_TIME, RECEIVER_ID, SENDER_ID) " +
				 "VALUES (?, ?, ?, ?, ?) ";
		
		try {
			conn = cm.getConnection();
			
			pStmt = conn.prepareStatement(query);
			
			pStmt.setInt(1, msg_send.getMsg_id());
			pStmt.setString(2, msg_send.getMsg_content());
			java.sql.Date date = getCurrentDatetime();
			pStmt.setDate(3, date);
			pStmt.setString(4, msg_send.getReceiver_id());
			pStmt.setString(5, msg_send.getSender_id());
			rs = pStmt.executeQuery();
			System.out.println("insertMessageSend 완료");
			
//			if (rs.next()) {
//	
//				int msg_id = rs.getInt("msg_id");
//				String msg_content = rs.getString("msg_content");
//				Date send_time = rs.getDate("send_time");
//				String receiver_id = rs.getString("receiver_id");
//				String sender_id = rs.getString("sender_id");
//				
//				MessageSend m_send = new MessageSend(msg_id, msg_content, send_time, receiver_id, sender_id);
//				
//				return m_send;
//			}	
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) 
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}	
		
		return null;
	}
}