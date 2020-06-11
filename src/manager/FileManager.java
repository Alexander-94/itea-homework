package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

	public FileManager() {
		super();
	}

	private String commands = "Commands: [dir, cd, cd .., exit, copy, help]";

	private void sortAndPrintFiles(File file) {
		File[] files = file.listFiles();
		List<String> ffs = new ArrayList<>();
		List<String> fds = new ArrayList<>();
		for (File f : files) {
			if (f.isDirectory()) {
				fds.add(f.getName().toString());
			} else {
				ffs.add(f.getName().toString());
			}
		}
		fds.sort(null);
		ffs.sort(null);
		fds.addAll(ffs);
		for (String s : fds) {
			System.out.println(s);
		}
	}

	public void start() {
		Scanner scanner = new Scanner(System.in);
		File file = new File(".");
		String command = null;

		while (true) {
			System.out.println("Enter command:");
			command = scanner.nextLine().toLowerCase().trim();
			if (command == null) {
				System.out.println("Something went wrong");
			} else {
				if ("help".equals(command)) {
					System.out.println(commands.toString());
				} else if ("dir".equals(command)) {
					sortAndPrintFiles(file);
				} else if ("cd ..".equals(command)) {
					if (file.getAbsoluteFile().getParentFile() != null) {
						file = file.getAbsoluteFile().getParentFile();
					}
					System.out.println(file.getAbsoluteFile().getParentFile());
					sortAndPrintFiles(file);
				} else if (command.startsWith("cd ")) {
					String dirName = command.substring(command.indexOf(" ") + 1);
					String oldFilePath = file.getAbsolutePath();

					if (!new File(oldFilePath + "\\" + dirName).exists()) {
						file = new File(oldFilePath);
						System.out.println("No such folder!");
					} else {
						file = new File(oldFilePath + "\\" + dirName);
						if (file.isDirectory()) {
							sortAndPrintFiles(file);
						} else {
							System.out.println("Not a folder!");
							file = new File(oldFilePath);
						}
					}
				} else if (command.startsWith("copy ")) {
					int firstSpaceIdx = command.indexOf(" ");
					int secondSpaceIdx = command.indexOf(" ", firstSpaceIdx + 1);
					String src = command.substring(firstSpaceIdx + 1, secondSpaceIdx);
					String dst = command.substring(secondSpaceIdx + 1);
					String srcPath = src;
					String dstPath = dst;

					if (!src.contains(":")) {
						srcPath = file.getAbsolutePath() + "\\" + src;
					}
					if (!dst.contains(":")) {
						dstPath = file.getAbsolutePath() + "\\" + dst;
					}

					File srcFile = new File(srcPath);
					File dstFile = new File(dstPath);

					try (InputStream in = new FileInputStream(srcFile);
							OutputStream out = new FileOutputStream(dstFile);) {
						int c;
						while ((c = in.read()) != -1) {
							out.write(c);
						}
					} catch (FileNotFoundException e) {
						System.out.println("Coldn't find the file!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if ("exit".equals(command)) {
					scanner.close();
					System.exit(0);
				} else {
					System.out.println("No such command!");
				}
			}

		}
	}

}
