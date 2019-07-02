package ru.badyavina.www;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class Sender {
	private String from;

	public void setFrom(String from) {
		this.from = from;
	}

	public void send() {
		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession("sftp_user_w", "sftp.vianor.ru", 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("ErAGAn35eN");
			
			session.connect();
			
			System.out.println(session.isConnected());
			Channel channel = session.openChannel("sftp");
			channel.connect();
			System.out.println(channel.isConnected());
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			String unixFrom = from.replace("\\", "/");
			System.out.println(unixFrom);
			sftpChannel.put(unixFrom, "/SFTP_W");
			System.out.println(sftpChannel.getHome());
			sftpChannel.start();
			
			
			System.out.println(sftpChannel.getHome());
			System.out.println(sftpChannel.isConnected());
			sftpChannel.exit();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}
}
