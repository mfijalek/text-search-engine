package pl.schibsted;

import pl.schibsted.model.Directory;
import pl.schibsted.services.CommunicationService;
import pl.schibsted.services.DirectoryService;

public class TextSearchEngineApplication {

	public static void main(String... args) {
		Directory directory = new Directory(args[0]);
		DirectoryService directoryService = new DirectoryService(directory);
		CommunicationService communicationService = new CommunicationService(directoryService);
		communicationService.execute();
	}

}
