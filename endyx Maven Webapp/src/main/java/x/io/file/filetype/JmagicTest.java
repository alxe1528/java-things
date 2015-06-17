package x.io.file.filetype;
import java.io.File;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;


public class JmagicTest {

	public static void main(String[] args) {
		
		 try {
			MagicMatch magic = Magic.getMagicMatch(new File("c:/ak48"), false, true);
			System.err.println(magic.getExtension());
		} catch (MagicParseException e) {
			e.printStackTrace();
		} catch (MagicMatchNotFoundException e) {
			e.printStackTrace();
		} catch (MagicException e) {
			e.printStackTrace();
		}  

	}

}
