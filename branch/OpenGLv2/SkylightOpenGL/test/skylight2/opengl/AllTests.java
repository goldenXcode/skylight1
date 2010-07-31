package skylight2.opengl;

import skylight2.opengl.files.QuickParseUtilTest;

public class AllTests {

	public static void main(String[] args) {
		new QuickParseUtilTest().test();

		new TestVertex().testVertexGeometryGroup();
		new TestVertex().testNestedVertexGeometryGroup();

		new TestTexturedNormaled().testTexturedNormaledGeometryGroup();
		new TestTexturedNormaled().testNestedTexturedNormaledGeometryGroup();

		new TestTexturedColoredNormaled().testTexturedColoredNormaledGeometryGroup();
		new TestTexturedColoredNormaled().testNestedTexturedColoredNormaledGeometryGroup();
	}

}
