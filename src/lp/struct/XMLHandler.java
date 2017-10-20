package lp.struct;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLHandler
{
	private File path;

	public XMLHandler(String path)
	{
		this.path = new File(path);
	}

	public boolean storeTest(Test test)
	{
		try
		{
			DocumentBuilder store = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document testDoc = store.newDocument();
			Element root = testDoc.createElement("test");
			root.setAttribute("id", ""+test.getId());
			testDoc.appendChild(root);

			for (Exo exo : test.getExos())
			{
				Element exoNode = testDoc.createElement("exo");
				exoNode.setAttribute("num", ""+exo.getNum());
				Element kProc = testDoc.createElement("kolb-processing");
				kProc.appendChild(testDoc.createTextNode(exo.getKolbProcessing()));
				exoNode.appendChild(kProc);
				Element kPerc = testDoc.createElement("kolb-perceiving");
				kPerc.appendChild(testDoc.createTextNode(exo.getKolbPerceiving()));
				exoNode.appendChild(kPerc);

				for (Question question : exo.getQuestions())
				{
					Element questNode = testDoc.createElement("question");
					questNode.setAttribute("num", ""+question.getNum());
					questNode.setAttribute("type", question.getType());
					Element content = testDoc.createElement("content");
					content.appendChild(testDoc.createTextNode(question.getContent()));
					questNode.appendChild(content);
					Element points = testDoc.createElement("points");
					points.appendChild(testDoc.createTextNode(""+question.getPoints()));
					questNode.appendChild(points);

					if(Question.QCM.equals(question.getType()))
					{
						for (String choice : question.getChoices())
						{
							Element choiceNode = testDoc.createElement("choice");
							choiceNode.appendChild(testDoc.createTextNode(choice));
							questNode.appendChild(choiceNode);
						}
					}

					exoNode.appendChild(questNode);
				}
				root.appendChild(exoNode);
			}// foreach exo

			Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.VERSION, "1.0");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.STANDALONE, "yes");
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			tr.transform(new DOMSource(testDoc), new StreamResult(path));
			return true;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
			return false;
		}
		catch (TransformerConfigurationException tce)
		{
			tce.printStackTrace();
			return false;
		}
		catch (TransformerException te)
		{
			te.printStackTrace();
			return false;
		}
	}
}
