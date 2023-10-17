package tw.idv.cha102.g7.customer.controller;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import tw.idv.cha102.g7.customer.entity.ChatMessage;
import tw.idv.cha102.g7.customer.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;


public class JedisHandleMessage {

	// 處理使用 Jedis 進行聊天訊息儲存和讀取操作
	static Jedis jedis;

	static Gson gson = new Gson();

	static {
		jedis = JedisUtil.getJedis();
	}

	// 從 Redis 中獲取聊天訊息的歷史記錄
	// key : (發送者名稱:接收者名稱)
	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		// jedis.lrange(key, 0, -1) 是 Jedis 中的一個方法，用於從 Redis 的列表（List）中獲取一個範圍的元素。
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	// 將指定發送者和接收者之間的聊天訊息標記為已讀
	public static List<String> getAllKey() {
		List<String> allKey = new ArrayList<String>();
		for (String key : jedis.keys("host*")) {
			if (jedis.type(key).equals("list")) {
				allKey.add(key);
			}
		}
		return allKey;
	}

	// 將聊天訊息存儲到 Redis 中
	public static void readAll(String sender, String receiver) {
		Gson gson = new Gson();
		List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		jedis.del(key);
		for (String oneChat : historyData) {
			ChatMessage cm = gson.fromJson(oneChat, ChatMessage.class);
			if (cm.getStatus() != null) {
				cm.setStatus("read");
			}
			jedis.rpush(key, gson.toJson(cm));
		}
	}


	public static void saveChatMessage(ChatMessage message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		if ("host".equals(message.getReceiver())) {
			jedis.rpush("host:" + message.getSender(), gson.toJson(message));
			jedis.rpush("member:" + message.getSender(), gson.toJson(message));
		} else {
			jedis.rpush("host:" + message.getReceiver(), gson.toJson(message));
			jedis.rpush("member:" + message.getReceiver(), gson.toJson(message));
		}
		jedis.close();
	}

	/**
	 * 獲得聊天室清單
	 *
	 * @return 聊天室清單
	 */
	public static List<String> getChatRoomList() {
		List<String> allKey = new ArrayList<String>();
		for (String key : jedis.keys("host*")) {
			if (jedis.type(key).equals("list")) {
				// 將 "host:"移除, 僅保留會員名稱
				allKey.add(key.replaceAll("host:", ""));
			}
		}
		return allKey;
	}

	/**
	 * 會員獲取聊天訊息
	 *
	 * @param member 會員名稱
	 * @return 歷史訊息
	 */
	public static List<String> getMemberMemberMsg(String member) {
		String key = "member:" + member;
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	/**
	 * 客服獲取會員聊天訊息
	 *
	 * @param member 會員名稱
	 * @return 歷史訊息
	 */
	public static List<String> getHostMemberMsg(String member) {
		String key = "host:" + member;
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	/**
	 * 客服獲取會員最後聊天訊息
	 *
	 * @param member 會員名稱
	 * @return 最後一條訊息
	 */
	public static ChatMessage getHostMemeberLastMsg(String member) {
		String key = "host:" + member;
		List<String> lastRow = jedis.lrange(key, 0, -1);
		ChatMessage msg = null;
		if(lastRow != null && lastRow.size()>0) {
			msg = gson.fromJson(lastRow.get((lastRow.size()-1)), ChatMessage.class);
		}
		return msg;
	}

}