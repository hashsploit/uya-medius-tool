package net.hashsploit.ps2.uya.medius;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

public final class Options {
	
	@Command(name=UYAMediusTool.NAME, version=UYAMediusTool.VERSION, subcommands = {DecryptOptions.class, EncryptOptions.class})
    static class ParentCommand implements Runnable {
        public void run() { }
    }
	
	@Command(name="decrypt", aliases = {"d", "dec"}, description="Set the tool to decryption mode.")
	static class DecryptOptions implements Runnable {
		@Option(names={"-k", "--key"}, description="Key as a hexstring (64 bytes).", arity="1", paramLabel="<key>", required = false)
		public String key;
		@Option(names={"-m", "--message", "-p", "--packet"}, description="Encrypted message as a hexstring.", arity="1", paramLabel="<message>", required = true)
		public String message;
		
		public void run() {
			UYAMediusTool tool = new UYAMediusTool();
			System.out.println("Decrypted message: " + tool.decryptMessage(key, message));
		}
	}
	
	@Command(name="encrypt", aliases = {"e", "enc"}, description="Set the tool to encryption mode.")
	static class EncryptOptions implements Runnable {
		@Option(names={"-k", "--key"}, description="Key as a hexstring (64 bytes).", arity="1", paramLabel="<key>", required = false)
		public String key;
		@Option(names={"-m", "--message"}, description="Message as a hexstring to encrypt.", arity="1", paramLabel="<message>", required = true)
		public String message;
		
		public void run() {
			UYAMediusTool tool = new UYAMediusTool();
			System.out.println("Encrypted message: " + tool.encryptMessage(key, message));
		}
	}
	
}
