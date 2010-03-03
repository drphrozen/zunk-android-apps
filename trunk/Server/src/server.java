import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

class server
{
	public static void main(String argv[]) throws Exception
	{
		System.out.println("Starting server...");
		ServerSocket welcomeSocket = new ServerSocket(40071);
		int i = 1;

		while (true)
		{
			System.out.println("Waiting for connection...");
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Got connection.");

			String fileName = "log" + i++ + " " + new Date().toString().replace(':', '.');
			PrintWriter kmlFile = new PrintWriter(new FileWriter(fileName + ".kml"));
			PrintWriter logFile = new PrintWriter(new FileWriter(fileName + ".txt"));

			int pointNumber = 1;
			Date startTime = new Date();
			Date endTime;
			long minInterval = 1000000000;
			long maxInterval = 0;
			int messageCount = 0;

			try
			{
				kmlFile.println("<kml>");
				kmlFile.println("\t<Document>");
				long lastTime = new Date().getTime();
				while (connectionSocket.isClosed() == false)
				{
					DataInputStream in = new DataInputStream(connectionSocket.getInputStream());

					double coord1 = in.readDouble();
					double coord2 = in.readDouble();
					float accuracy = in.readFloat();

					long currentTime = new Date().getTime();
					long interval = currentTime - lastTime;
					if (interval > maxInterval)
					{
						maxInterval = interval;
					}
					if (interval < minInterval)
					{
						minInterval = interval;
					}
					messageCount++;

					System.out.println(DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date()) + "- " + coord1 + ", " + coord2 + " (" + accuracy + " in units of whoknows)");

					kmlFile.println("\t\t<Placemark>");
					kmlFile.println("\t\t\t<name>Point" + pointNumber++ + "</name>");
					kmlFile.println("\t\t\t<Point>");
					kmlFile.println("\t\t\t\t<coordinates>" + coord1 + "," + coord2 + ",0</coordinates>");
					kmlFile.println("\t\t\t</Point>");
					kmlFile.println("\t\t</Placemark>");
				}
			} catch (Exception e)
			{
			}

			endTime = new Date();

			kmlFile.println("\t</Document>");
			kmlFile.println("</kml>");
			kmlFile.close();

			logFile.println("Start time: " + startTime.toString());
			logFile.println("End time: " + endTime.toString());
			logFile.println("Messages: " + messageCount);
			logFile.println("Min. interval: " + minInterval + " ms.");
			logFile.println("Max. interval: " + maxInterval + " ms.");
			long totalTime = endTime.getTime() - startTime.getTime();
			long messagesPerSecond = (messageCount * 1000) / totalTime;
			logFile.println("Message per second: " + messagesPerSecond);
			logFile.close();

			System.out.println("Connection ended.");
		}
	}
}