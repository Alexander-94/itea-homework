package manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FileManager {

	public FileManager() {
		super();
	}

	private String commands = "Commands: [dir, cd, cd .., exit, copy, help]";

	private void sortAndPrintFiles(Path filePath) {
		Stream<Path> files = null;
		List<String> ffs = new ArrayList<>();
		List<String> fds = new ArrayList<>();

		try {
			files = Files.list(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Consumer<Path> p = f -> {
			if (Files.isDirectory(f)) {
				fds.add(f.getFileName().toString());
			} else {
				ffs.add(f.getFileName().toString());
			}
		};
		files.forEach(p);

		Collections.sort(fds);
		Collections.sort(ffs);
		fds.addAll(ffs);
		for (String s : fds) {
			System.out.println(s);
		}
	}

	private void copy(Path source, Path dest) {
		try {
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.out.println("Coldn't find the file!");
		}
	}

	public void copyFolder(Path src, Path dest) {
		try (Stream<Path> stream = Files.walk(src)) {
			stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
		} catch (Exception e) {
			System.out.println("Folder copy error!");
		}
	}

	public void start() {
		Scanner scanner = new Scanner(System.in);
		Path filePath = Paths.get(".");
		String command = null;

		while (true) {
			System.out.println("Enter command:");
			try {
				command = scanner.nextLine().toLowerCase().trim();
			} catch (Exception e) {
				System.out.println("No line was found");
			}

			if (command.equals("")) {
				System.out.println("No such command!");
			} else {
				if ("help".equals(command)) {
					System.out.println(commands.toString());
				} else if ("dir".equals(command)) {
					sortAndPrintFiles(filePath);
				} else if ("cd ..".equals(command)) {
					if (filePath.toAbsolutePath().getParent() != null) {
						try {
							filePath = filePath.toRealPath().getParent();
						} catch (IOException e) {
							System.out.println("Folder doesn't exist");
						}
					}
					sortAndPrintFiles(filePath);
				} else if (command.startsWith("cd ")) {
					String dirName = command.substring(command.indexOf(" ") + 1);
					String oldFilePath = null;
					try {
						oldFilePath = filePath.toRealPath().toString();
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					if (!Files.exists(Paths.get(filePath + "\\" + dirName))) {
						System.out.println("No such folder!");
					} else {
						filePath = Paths.get(filePath + "\\" + dirName);
						if (Files.isDirectory(filePath)) {
							sortAndPrintFiles(filePath);
						} else {
							System.out.println("Not a folder!");
							filePath = Paths.get(oldFilePath);
						}
					}
				} else if (command.startsWith("copy ")) {
					int firstSpaceIdx = command.indexOf(" ");
					int secondSpaceIdx = command.indexOf(" ", firstSpaceIdx + 1);
					String src = command.substring(firstSpaceIdx + 1, secondSpaceIdx);
					String dst = command.substring(secondSpaceIdx + 1);
					Path srcPath = Paths.get(src);
					Path dstPath = Paths.get(dst);
					String currPath = null;

					try {
						currPath = filePath.toRealPath().toString();
					} catch (Exception e) {
						System.out.println("filePath is wrong..");
						e.printStackTrace();
					}

					if (!src.contains(":")) {
						srcPath = Paths.get(currPath + "\\" + src);
					}
					if (!dst.contains(":")) {
						dstPath = Paths.get(currPath + "\\" + dst);
					}

					if (!Files.isDirectory(srcPath)) {
						copy(srcPath, dstPath);
					} else {
						copyFolder(srcPath, dstPath);
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
