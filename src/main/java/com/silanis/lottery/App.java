package com.silanis.lottery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws Exception {
		
       checkJavaVersion();
		
		ILotteryService lotteryService = new LotteryService();
		lotteryService.reset();
		while (true) {
			helpOnAvailableCommands(lotteryService);
			String input = readLine("Enter your command : ");
			if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
				log("Exit!");
				System.exit(0);
			} else {
				if (input.isEmpty()) {
					log("No command typed. Please  retry.");
				} else {
					try {
						String[] params = input.split("(\\s)+");
						List<String> argsFiltered = Arrays.stream(params).filter(e -> e.trim().length() > 0)
								.collect(Collectors.<String> toList());
						log("Proceessing cmd : %s ", argsFiltered.get(0));

						process(lotteryService, argsFiltered);
					} catch (LotteryException ignore) {
						log(ignore.getMessage());
					}
				}
			}
			log("-----------\n");
		}

	}

	private static void helpOnAvailableCommands(ILotteryService lotteryService) {
		log("Here are your commands. Press [enter] key after typing your desired command alog with his Spaced arguments");
		log("Command p|purchase [amountOptional][ticketNoOptional] allows you to purchase tickets e.g purchase bret 10. Your ball no is in range 1..50. Nb Ball number is optional.");
		log("Command w|winners allows you to display winners. e.g winners");
		log("Command d|draw allows you to pick randomly 3 winners. e.g draw");
		log("Command r|reset [amountOptional]allows you to reset the session. e.g reset 250");
		log("Command q|quit allows you to end the session. e.g quit");
		log("List of available balls for purchasing : \n %s", availableBalls(lotteryService));
	}

	final static void checkJavaVersion() {
		double javaVersion = getVersion();
			if(javaVersion < 1.8){
				log("Please ensure your java version [%s] is equal or greather than 1.8", System.getProperty("java.version"));
				log("Exit!");
				System.exit(0);
			}
	}

	final static void process(ILotteryService service, List<String> argsFiltered) {
		String cmd = argsFiltered.get(0).toLowerCase();

		switch (cmd) {
		case "p":
		case "purchase":
			if (argsFiltered.size() == 2) {
				service.purchase(argsFiltered.get(1));
			} else if (argsFiltered.size() >= 3) {
				service.purchase(argsFiltered.get(1), toInt(argsFiltered.get(2)));
			}
			break;
		case "w":
		case "winners":
			Map<Integer, Winner> winners = service.peekWinners();
			log("1st ball     2nd ball     3rd ball");
			log("%s    %s     %s", winners.get(1), winners.get(2), winners.get(3));
			break;
		case "d":
		case "draw":
			service.drawnThreeBalls();
			break;
		case "r":
		case "reset":
			if (argsFiltered.size() == 1) {
				service.reset();
			} else if (argsFiltered.size() >= 2) {
				service.reset(toInt(argsFiltered.get(1)));
			}			
			break;
		case "t":
		case "test":
			service.purchaseAll();
			break;
		default:
			throw new LotteryException(
					String.format("Command '%s' is not supported. Please retry.", argsFiltered.get(0)));
		}
		log("Command %s Completed Successfully", argsFiltered.get(0));

	}

	private static int toInt(String params) {
		try {
			return Integer.parseInt(params.trim());
		} catch (NumberFormatException e) {
			throw new LotteryException(String.format("This ball %s is not a valid ball number. Retry.", params));
		}
	}

	private static void log(String input, Object... args) {
		System.out.println(String.format(input, args));
	}

	private static String readLine(String format, Object... args) throws IOException {
		// workaround
		// http://stackoverflow.com/questions/4203646/system-console-returns-null
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		System.out.print(String.format(format, args));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	private static StringBuilder availableBalls(ILotteryService lotteryService) {
		final StringBuilder listAsStr = new StringBuilder("[");

		lotteryService.remainBallsForPurchase().forEach(ball -> {
			if (listAsStr.length() > 1) {
				listAsStr.append(", ");
			}
			listAsStr.append(ball);
		});
		listAsStr.append("]");
		return listAsStr;
	}

	static double getVersion() {
		String version = System.getProperty("java.version");
		int pos = version.indexOf('.');
		pos = version.indexOf('.', pos + 1);
		return Double.parseDouble(version.substring(0, pos));
	}

}