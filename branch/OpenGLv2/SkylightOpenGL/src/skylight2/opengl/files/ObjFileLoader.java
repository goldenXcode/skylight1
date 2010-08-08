package skylight2.opengl.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

import skylight2.opengl.Geometry;
import skylight2.opengl.TexturedNormaledBuffer;
import skylight2.opengl.TexturedNormaledBuffer.IncompleteTrianglesGeometry;

/**
 * Loads 3D model from a WaveFront OBJ file.
 * 
 */
public class ObjFileLoader {
	// some inner classes to model the data in the obj file
	private static class ModelCoordinates {
		float x, y, z;

		public ModelCoordinates(float anX, float aY, float aZ) {
			x = anX;
			y = aY;
			z = aZ;
		}
	}

	private static class TextureCoordinates {
		float u, v;

		public TextureCoordinates(float aU, float aV) {
			u = aU;
			v = aV;
		}
	}

	private static class Normal {
		float x, y, z;

		public Normal(float anX, float aY, float aZ) {
			x = anX;
			y = aY;
			z = aZ;
		}
	}

	private static class Vertex {
		ModelCoordinates modelCoordinates;

		TextureCoordinates textureCoordinates;

		Normal normal;

		public Vertex(ModelCoordinates aModelCoordinates, TextureCoordinates aTextureCoordinates, Normal aNormal) {
			modelCoordinates = aModelCoordinates;
			textureCoordinates = aTextureCoordinates;
			normal = aNormal;
		}
	}

	private static class Face {
		Vertex v1, v2, v3;

		public Face(Vertex aV1, Vertex aV2, Vertex aV3) {
			v1 = aV1;
			v2 = aV2;
			v3 = aV3;
		}
	}

	private static final int EOF = -1;

	private Vector faces = new Vector();

	public ObjFileLoader(final InputStream anInputStream) throws IOException {
		loadModelFromInputStream(anInputStream);
	}

	private void loadModelFromInputStream(final InputStream anInputStream) throws IOException {
		final Vector modelCoordinates = new Vector();

		final Vector texturesCoordinates = new Vector();

		final Vector normals = new Vector();

		final InputStreamReader br = new InputStreamReader(anInputStream);
		String[] line;
		while ((line = readLine(br)) != null) {
//			System.out.println(line[0]);
			// if the line was blank there will be no parts to it
			if (line[0] == null) {
				continue;
			}

			if (line[0].equals("v")) {
				modelCoordinates.addElement(new ModelCoordinates(QuickParseUtil.parseFloat(line[1]), QuickParseUtil.parseFloat(line[2]), QuickParseUtil
						.parseFloat(line[3])));
			} else if (line[0].equals("vt")) {
				texturesCoordinates.addElement(new TextureCoordinates(QuickParseUtil.parseFloat(line[1]), 1f - QuickParseUtil.parseFloat(line[2])));
			} else if (line[0].equals("vn")) {
				normals.addElement(new Normal(QuickParseUtil.parseFloat(line[1]), QuickParseUtil.parseFloat(line[2]), QuickParseUtil.parseFloat(line[3])));
			} else if (line[0].equals("f")) {
//				System.out.println(line[0] + ";" + line[1] + ";" + line[2] + ";" + line[1]);
//				System.out.println(line[0] + ":" + QuickParseUtil.parseInteger(line[1]) + " " + QuickParseUtil.parseInteger(line[2]) + " "
//						+ QuickParseUtil.parseInteger(line[1]));
//				System.out.println(modelCoordinates.size() + "," + texturesCoordinates.size() + "," + normals.size());
				final Vertex v1 =
						new Vertex((ModelCoordinates) modelCoordinates.elementAt(QuickParseUtil.parseInteger(line[1]) - 1), (TextureCoordinates) texturesCoordinates
								.elementAt(QuickParseUtil.parseInteger(line[2]) - 1), (Normal) normals.elementAt(QuickParseUtil.parseInteger(line[3]) - 1));
				final Vertex v2 =
						new Vertex((ModelCoordinates) modelCoordinates.elementAt(QuickParseUtil.parseInteger(line[4]) - 1), (TextureCoordinates) texturesCoordinates
								.elementAt(QuickParseUtil.parseInteger(line[5]) - 1), (Normal) normals.elementAt(QuickParseUtil.parseInteger(line[6]) - 1));
				final Vertex v3 =
						new Vertex((ModelCoordinates) modelCoordinates.elementAt(QuickParseUtil.parseInteger(line[7]) - 1), (TextureCoordinates) texturesCoordinates
								.elementAt(QuickParseUtil.parseInteger(line[8]) - 1), (Normal) normals.elementAt(QuickParseUtil.parseInteger(line[9]) - 1));
				faces.addElement(new Face(v1, v2, v3));
			}
		}
	}

	private String[] readLine(Reader aReader) throws IOException {
		final String[] result = new String[10];
		final StringBuffer linePart = new StringBuffer();
		int partIndex = 0;
		int c;
		while (true) {
			c = aReader.read();
//			System.out.println(c);
			if ((char) c == ',' || (char) c == '/' || (char) c == ' ' || (char) c == '\n' || (char) c == '\r' || c == EOF) {
				result[partIndex++] = linePart.toString();
				linePart.setLength(0);
				if (partIndex >= result.length || (char) c == '\n' || (char) c == '\r' || c == EOF) {
					break;
				}
			} else {
				linePart.append((char) c);
			}
		}

		// if the end of the line was reached, and no line was read, then return null
		if (c == EOF && partIndex == 1 && result[0].equals("")) {
			return null;
		}

		return result;
	}

	public Geometry createGeometry(final TexturedNormaledBuffer aTexturedNormaledBuffer) {
		IncompleteTrianglesGeometry geometry = aTexturedNormaledBuffer.startTrianglesGeometry();

		for (int f = 0; f < faces.size(); f++) {
			Face face = (Face) faces.elementAt(f);

			final Vertex v1 = face.v1;
			final Vertex v2 = face.v2;
			final Vertex v3 = face.v3;

			final ModelCoordinates v1ModelCoordinates = v1.modelCoordinates;
			final TextureCoordinates v1TextureCoordinates = v1.textureCoordinates;
			final Normal v1Normal = v1.normal;
			final ModelCoordinates v2ModelCoordinates = v2.modelCoordinates;
			final TextureCoordinates v2TextureCoordinates = v2.textureCoordinates;
			final Normal v2Normal = v2.normal;
			final ModelCoordinates v3ModelCoordinates = v3.modelCoordinates;
			final TextureCoordinates v3TextureCoordinates = v3.textureCoordinates;
			final Normal v3Normal = v3.normal;
			geometry =
					geometry
							.addTriangle(v1ModelCoordinates.x, v1ModelCoordinates.y, v1ModelCoordinates.z, v2ModelCoordinates.x, v2ModelCoordinates.y, v2ModelCoordinates.z, v3ModelCoordinates.x, v3ModelCoordinates.y, v3ModelCoordinates.z)
							.setTextures(v1TextureCoordinates.u, v1TextureCoordinates.v, v2TextureCoordinates.u, v2TextureCoordinates.v, v3TextureCoordinates.u, v3TextureCoordinates.v)
							.setNormals(v1Normal.x, v1Normal.y, v1Normal.z, v2Normal.x, v2Normal.y, v2Normal.z, v3Normal.x, v3Normal.y, v3Normal.z);
		}

		return geometry.endGeometry();
	}
}
