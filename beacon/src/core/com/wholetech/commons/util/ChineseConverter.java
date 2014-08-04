package com.wholetech.commons.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ChineseConverter {

	private static Map<String, Integer> spellMap = new LinkedHashMap<String, Integer>(400, 1);

	private static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万",
			"拾", "佰", "仟",
		"亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

	static {
		ChineseConverter.spellPut("a", -20319);
		ChineseConverter.spellPut("ai", -20317);
		ChineseConverter.spellPut("an", -20304);
		ChineseConverter.spellPut("ang", -20295);
		ChineseConverter.spellPut("ao", -20292);
		ChineseConverter.spellPut("ba", -20283);
		ChineseConverter.spellPut("bai", -20265);
		ChineseConverter.spellPut("ban", -20257);
		ChineseConverter.spellPut("bang", -20242);
		ChineseConverter.spellPut("bao", -20230);
		ChineseConverter.spellPut("bei", -20051);
		ChineseConverter.spellPut("ben", -20036);
		ChineseConverter.spellPut("beng", -20032);
		ChineseConverter.spellPut("bi", -20026);
		ChineseConverter.spellPut("bian", -20002);
		ChineseConverter.spellPut("biao", -19990);
		ChineseConverter.spellPut("bie", -19986);
		ChineseConverter.spellPut("bin", -19982);
		ChineseConverter.spellPut("bing", -19976);
		ChineseConverter.spellPut("bo", -19805);
		ChineseConverter.spellPut("bu", -19784);
		ChineseConverter.spellPut("ca", -19775);
		ChineseConverter.spellPut("cai", -19774);
		ChineseConverter.spellPut("can", -19763);
		ChineseConverter.spellPut("cang", -19756);
		ChineseConverter.spellPut("cao", -19751);
		ChineseConverter.spellPut("ce", -19746);
		ChineseConverter.spellPut("ceng", -19741);
		ChineseConverter.spellPut("cha", -19739);
		ChineseConverter.spellPut("chai", -19728);
		ChineseConverter.spellPut("chan", -19725);
		ChineseConverter.spellPut("chang", -19715);
		ChineseConverter.spellPut("chao", -19540);
		ChineseConverter.spellPut("che", -19531);
		ChineseConverter.spellPut("chen", -19525);
		ChineseConverter.spellPut("cheng", -19515);
		ChineseConverter.spellPut("chi", -19500);
		ChineseConverter.spellPut("chong", -19484);
		ChineseConverter.spellPut("chou", -19479);
		ChineseConverter.spellPut("chu", -19467);
		ChineseConverter.spellPut("chuai", -19289);
		ChineseConverter.spellPut("chuan", -19288);
		ChineseConverter.spellPut("chuang", -19281);
		ChineseConverter.spellPut("chui", -19275);
		ChineseConverter.spellPut("chun", -19270);
		ChineseConverter.spellPut("chuo", -19263);
		ChineseConverter.spellPut("ci", -19261);
		ChineseConverter.spellPut("cong", -19249);
		ChineseConverter.spellPut("cou", -19243);
		ChineseConverter.spellPut("cu", -19242);
		ChineseConverter.spellPut("cuan", -19238);
		ChineseConverter.spellPut("cui", -19235);
		ChineseConverter.spellPut("cun", -19227);
		ChineseConverter.spellPut("cuo", -19224);
		ChineseConverter.spellPut("da", -19218);
		ChineseConverter.spellPut("dai", -19212);
		ChineseConverter.spellPut("dan", -19038);
		ChineseConverter.spellPut("dang", -19023);
		ChineseConverter.spellPut("dao", -19018);
		ChineseConverter.spellPut("de", -19006);
		ChineseConverter.spellPut("deng", -19003);
		ChineseConverter.spellPut("di", -18996);
		ChineseConverter.spellPut("dian", -18977);
		ChineseConverter.spellPut("diao", -18961);
		ChineseConverter.spellPut("die", -18952);
		ChineseConverter.spellPut("ding", -18783);
		ChineseConverter.spellPut("diu", -18774);
		ChineseConverter.spellPut("dong", -18773);
		ChineseConverter.spellPut("dou", -18763);
		ChineseConverter.spellPut("du", -18756);
		ChineseConverter.spellPut("duan", -18741);
		ChineseConverter.spellPut("dui", -18735);
		ChineseConverter.spellPut("dun", -18731);
		ChineseConverter.spellPut("duo", -18722);
		ChineseConverter.spellPut("e", -18710);
		ChineseConverter.spellPut("en", -18697);
		ChineseConverter.spellPut("er", -18696);
		ChineseConverter.spellPut("fa", -18526);
		ChineseConverter.spellPut("fan", -18518);
		ChineseConverter.spellPut("fang", -18501);
		ChineseConverter.spellPut("fei", -18490);
		ChineseConverter.spellPut("fen", -18478);
		ChineseConverter.spellPut("feng", -18463);
		ChineseConverter.spellPut("fo", -18448);
		ChineseConverter.spellPut("fou", -18447);
		ChineseConverter.spellPut("fu", -18446);
		ChineseConverter.spellPut("ga", -18239);
		ChineseConverter.spellPut("gai", -18237);
		ChineseConverter.spellPut("gan", -18231);
		ChineseConverter.spellPut("gang", -18220);
		ChineseConverter.spellPut("gao", -18211);
		ChineseConverter.spellPut("ge", -18201);
		ChineseConverter.spellPut("gei", -18184);
		ChineseConverter.spellPut("gen", -18183);
		ChineseConverter.spellPut("geng", -18181);
		ChineseConverter.spellPut("gong", -18012);
		ChineseConverter.spellPut("gou", -17997);
		ChineseConverter.spellPut("gu", -17988);
		ChineseConverter.spellPut("gua", -17970);
		ChineseConverter.spellPut("guai", -17964);
		ChineseConverter.spellPut("guan", -17961);
		ChineseConverter.spellPut("guang", -17950);
		ChineseConverter.spellPut("gui", -17947);
		ChineseConverter.spellPut("gun", -17931);
		ChineseConverter.spellPut("guo", -17928);
		ChineseConverter.spellPut("ha", -17922);
		ChineseConverter.spellPut("hai", -17759);
		ChineseConverter.spellPut("han", -17752);
		ChineseConverter.spellPut("hang", -17733);
		ChineseConverter.spellPut("hao", -17730);
		ChineseConverter.spellPut("he", -17721);
		ChineseConverter.spellPut("hei", -17703);
		ChineseConverter.spellPut("hen", -17701);
		ChineseConverter.spellPut("heng", -17697);
		ChineseConverter.spellPut("hong", -17692);
		ChineseConverter.spellPut("hou", -17683);
		ChineseConverter.spellPut("hu", -17676);
		ChineseConverter.spellPut("hua", -17496);
		ChineseConverter.spellPut("huai", -17487);
		ChineseConverter.spellPut("huan", -17482);
		ChineseConverter.spellPut("huang", -17468);
		ChineseConverter.spellPut("hui", -17454);
		ChineseConverter.spellPut("hun", -17433);
		ChineseConverter.spellPut("huo", -17427);
		ChineseConverter.spellPut("ji", -17417);
		ChineseConverter.spellPut("jia", -17202);
		ChineseConverter.spellPut("jian", -17185);
		ChineseConverter.spellPut("jiang", -16983);
		ChineseConverter.spellPut("jiao", -16970);
		ChineseConverter.spellPut("jie", -16942);
		ChineseConverter.spellPut("jin", -16915);
		ChineseConverter.spellPut("jing", -16733);
		ChineseConverter.spellPut("jiong", -16708);
		ChineseConverter.spellPut("jiu", -16706);
		ChineseConverter.spellPut("ju", -16689);
		ChineseConverter.spellPut("juan", -16664);
		ChineseConverter.spellPut("jue", -16657);
		ChineseConverter.spellPut("jun", -16647);
		ChineseConverter.spellPut("ka", -16474);
		ChineseConverter.spellPut("kai", -16470);
		ChineseConverter.spellPut("kan", -16465);
		ChineseConverter.spellPut("kang", -16459);
		ChineseConverter.spellPut("kao", -16452);
		ChineseConverter.spellPut("ke", -16448);
		ChineseConverter.spellPut("ken", -16433);
		ChineseConverter.spellPut("keng", -16429);
		ChineseConverter.spellPut("kong", -16427);
		ChineseConverter.spellPut("kou", -16423);
		ChineseConverter.spellPut("ku", -16419);
		ChineseConverter.spellPut("kua", -16412);
		ChineseConverter.spellPut("kuai", -16407);
		ChineseConverter.spellPut("kuan", -16403);
		ChineseConverter.spellPut("kuang", -16401);
		ChineseConverter.spellPut("kui", -16393);
		ChineseConverter.spellPut("kun", -16220);
		ChineseConverter.spellPut("kuo", -16216);
		ChineseConverter.spellPut("la", -16212);
		ChineseConverter.spellPut("lai", -16205);
		ChineseConverter.spellPut("lan", -16202);
		ChineseConverter.spellPut("lang", -16187);
		ChineseConverter.spellPut("lao", -16180);
		ChineseConverter.spellPut("le", -16171);
		ChineseConverter.spellPut("lei", -16169);
		ChineseConverter.spellPut("leng", -16158);
		ChineseConverter.spellPut("li", -16155);
		ChineseConverter.spellPut("lia", -15959);
		ChineseConverter.spellPut("lian", -15958);
		ChineseConverter.spellPut("liang", -15944);
		ChineseConverter.spellPut("liao", -15933);
		ChineseConverter.spellPut("lie", -15920);
		ChineseConverter.spellPut("lin", -15915);
		ChineseConverter.spellPut("ling", -15903);
		ChineseConverter.spellPut("liu", -15889);
		ChineseConverter.spellPut("long", -15878);
		ChineseConverter.spellPut("lou", -15707);
		ChineseConverter.spellPut("lu", -15701);
		ChineseConverter.spellPut("lv", -15681);
		ChineseConverter.spellPut("luan", -15667);
		ChineseConverter.spellPut("lue", -15661);
		ChineseConverter.spellPut("lun", -15659);
		ChineseConverter.spellPut("luo", -15652);
		ChineseConverter.spellPut("ma", -15640);
		ChineseConverter.spellPut("mai", -15631);
		ChineseConverter.spellPut("man", -15625);
		ChineseConverter.spellPut("mang", -15454);
		ChineseConverter.spellPut("mao", -15448);
		ChineseConverter.spellPut("me", -15436);
		ChineseConverter.spellPut("mei", -15435);
		ChineseConverter.spellPut("men", -15419);
		ChineseConverter.spellPut("meng", -15416);
		ChineseConverter.spellPut("mi", -15408);
		ChineseConverter.spellPut("mian", -15394);
		ChineseConverter.spellPut("miao", -15385);
		ChineseConverter.spellPut("mie", -15377);
		ChineseConverter.spellPut("min", -15375);
		ChineseConverter.spellPut("ming", -15369);
		ChineseConverter.spellPut("miu", -15363);
		ChineseConverter.spellPut("mo", -15362);
		ChineseConverter.spellPut("mou", -15183);
		ChineseConverter.spellPut("mu", -15180);
		ChineseConverter.spellPut("na", -15165);
		ChineseConverter.spellPut("nai", -15158);
		ChineseConverter.spellPut("nan", -15153);
		ChineseConverter.spellPut("nang", -15150);
		ChineseConverter.spellPut("nao", -15149);
		ChineseConverter.spellPut("ne", -15144);
		ChineseConverter.spellPut("nei", -15143);
		ChineseConverter.spellPut("nen", -15141);
		ChineseConverter.spellPut("neng", -15140);
		ChineseConverter.spellPut("ni", -15139);
		ChineseConverter.spellPut("nian", -15128);
		ChineseConverter.spellPut("niang", -15121);
		ChineseConverter.spellPut("niao", -15119);
		ChineseConverter.spellPut("nie", -15117);
		ChineseConverter.spellPut("nin", -15110);
		ChineseConverter.spellPut("ning", -15109);
		ChineseConverter.spellPut("niu", -14941);
		ChineseConverter.spellPut("nong", -14937);
		ChineseConverter.spellPut("nu", -14933);
		ChineseConverter.spellPut("nv", -14930);
		ChineseConverter.spellPut("nuan", -14929);
		ChineseConverter.spellPut("nue", -14928);
		ChineseConverter.spellPut("nuo", -14926);
		ChineseConverter.spellPut("o", -14922);
		ChineseConverter.spellPut("ou", -14921);
		ChineseConverter.spellPut("pa", -14914);
		ChineseConverter.spellPut("pai", -14908);
		ChineseConverter.spellPut("pan", -14902);
		ChineseConverter.spellPut("pang", -14894);
		ChineseConverter.spellPut("pao", -14889);
		ChineseConverter.spellPut("pei", -14882);
		ChineseConverter.spellPut("pen", -14873);
		ChineseConverter.spellPut("peng", -14871);
		ChineseConverter.spellPut("pi", -14857);
		ChineseConverter.spellPut("pian", -14678);
		ChineseConverter.spellPut("piao", -14674);
		ChineseConverter.spellPut("pie", -14670);
		ChineseConverter.spellPut("pin", -14668);
		ChineseConverter.spellPut("ping", -14663);
		ChineseConverter.spellPut("po", -14654);
		ChineseConverter.spellPut("pu", -14645);
		ChineseConverter.spellPut("qi", -14630);
		ChineseConverter.spellPut("qia", -14594);
		ChineseConverter.spellPut("qian", -14429);
		ChineseConverter.spellPut("qiang", -14407);
		ChineseConverter.spellPut("qiao", -14399);
		ChineseConverter.spellPut("qie", -14384);
		ChineseConverter.spellPut("qin", -14379);
		ChineseConverter.spellPut("qing", -14368);
		ChineseConverter.spellPut("qiong", -14355);
		ChineseConverter.spellPut("qiu", -14353);
		ChineseConverter.spellPut("qu", -14345);
		ChineseConverter.spellPut("quan", -14170);
		ChineseConverter.spellPut("que", -14159);
		ChineseConverter.spellPut("qun", -14151);
		ChineseConverter.spellPut("ran", -14149);
		ChineseConverter.spellPut("rang", -14145);
		ChineseConverter.spellPut("rao", -14140);
		ChineseConverter.spellPut("re", -14137);
		ChineseConverter.spellPut("ren", -14135);
		ChineseConverter.spellPut("reng", -14125);
		ChineseConverter.spellPut("ri", -14123);
		ChineseConverter.spellPut("rong", -14122);
		ChineseConverter.spellPut("rou", -14112);
		ChineseConverter.spellPut("ru", -14109);
		ChineseConverter.spellPut("ruan", -14099);
		ChineseConverter.spellPut("rui", -14097);
		ChineseConverter.spellPut("run", -14094);
		ChineseConverter.spellPut("ruo", -14092);
		ChineseConverter.spellPut("sa", -14090);
		ChineseConverter.spellPut("sai", -14087);
		ChineseConverter.spellPut("san", -14083);
		ChineseConverter.spellPut("sang", -13917);
		ChineseConverter.spellPut("sao", -13914);
		ChineseConverter.spellPut("se", -13910);
		ChineseConverter.spellPut("sen", -13907);
		ChineseConverter.spellPut("seng", -13906);
		ChineseConverter.spellPut("sha", -13905);
		ChineseConverter.spellPut("shai", -13896);
		ChineseConverter.spellPut("shan", -13894);
		ChineseConverter.spellPut("shang", -13878);
		ChineseConverter.spellPut("shao", -13870);
		ChineseConverter.spellPut("she", -13859);
		ChineseConverter.spellPut("shen", -13847);
		ChineseConverter.spellPut("sheng", -13831);
		ChineseConverter.spellPut("shi", -13658);
		ChineseConverter.spellPut("shou", -13611);
		ChineseConverter.spellPut("shu", -13601);
		ChineseConverter.spellPut("shua", -13406);
		ChineseConverter.spellPut("shuai", -13404);
		ChineseConverter.spellPut("shuan", -13400);
		ChineseConverter.spellPut("shuang", -13398);
		ChineseConverter.spellPut("shui", -13395);
		ChineseConverter.spellPut("shun", -13391);
		ChineseConverter.spellPut("shuo", -13387);
		ChineseConverter.spellPut("si", -13383);
		ChineseConverter.spellPut("song", -13367);
		ChineseConverter.spellPut("sou", -13359);
		ChineseConverter.spellPut("su", -13356);
		ChineseConverter.spellPut("suan", -13343);
		ChineseConverter.spellPut("sui", -13340);
		ChineseConverter.spellPut("sun", -13329);
		ChineseConverter.spellPut("suo", -13326);
		ChineseConverter.spellPut("ta", -13318);
		ChineseConverter.spellPut("tai", -13147);
		ChineseConverter.spellPut("tan", -13138);
		ChineseConverter.spellPut("tang", -13120);
		ChineseConverter.spellPut("tao", -13107);
		ChineseConverter.spellPut("te", -13096);
		ChineseConverter.spellPut("teng", -13095);
		ChineseConverter.spellPut("ti", -13091);
		ChineseConverter.spellPut("tian", -13076);
		ChineseConverter.spellPut("tiao", -13068);
		ChineseConverter.spellPut("tie", -13063);
		ChineseConverter.spellPut("ting", -13060);
		ChineseConverter.spellPut("tong", -12888);
		ChineseConverter.spellPut("tou", -12875);
		ChineseConverter.spellPut("tu", -12871);
		ChineseConverter.spellPut("tuan", -12860);
		ChineseConverter.spellPut("tui", -12858);
		ChineseConverter.spellPut("tun", -12852);
		ChineseConverter.spellPut("tuo", -12849);
		ChineseConverter.spellPut("wa", -12838);
		ChineseConverter.spellPut("wai", -12831);
		ChineseConverter.spellPut("wan", -12829);
		ChineseConverter.spellPut("wang", -12812);
		ChineseConverter.spellPut("wei", -12802);
		ChineseConverter.spellPut("wen", -12607);
		ChineseConverter.spellPut("weng", -12597);
		ChineseConverter.spellPut("wo", -12594);
		ChineseConverter.spellPut("wu", -12585);
		ChineseConverter.spellPut("xi", -12556);
		ChineseConverter.spellPut("xia", -12359);
		ChineseConverter.spellPut("xian", -12346);
		ChineseConverter.spellPut("xiang", -12320);
		ChineseConverter.spellPut("xiao", -12300);
		ChineseConverter.spellPut("xie", -12120);
		ChineseConverter.spellPut("xin", -12099);
		ChineseConverter.spellPut("xing", -12089);
		ChineseConverter.spellPut("xiong", -12074);
		ChineseConverter.spellPut("xiu", -12067);
		ChineseConverter.spellPut("xu", -12058);
		ChineseConverter.spellPut("xuan", -12039);
		ChineseConverter.spellPut("xue", -11867);
		ChineseConverter.spellPut("xun", -11861);
		ChineseConverter.spellPut("ya", -11847);
		ChineseConverter.spellPut("yan", -11831);
		ChineseConverter.spellPut("yang", -11798);
		ChineseConverter.spellPut("yao", -11781);
		ChineseConverter.spellPut("ye", -11604);
		ChineseConverter.spellPut("yi", -11589);
		ChineseConverter.spellPut("yin", -11536);
		ChineseConverter.spellPut("ying", -11358);
		ChineseConverter.spellPut("yo", -11340);
		ChineseConverter.spellPut("yong", -11339);
		ChineseConverter.spellPut("you", -11324);
		ChineseConverter.spellPut("yu", -11303);
		ChineseConverter.spellPut("yuan", -11097);
		ChineseConverter.spellPut("yue", -11077);
		ChineseConverter.spellPut("yun", -11067);
		ChineseConverter.spellPut("za", -11055);
		ChineseConverter.spellPut("zai", -11052);
		ChineseConverter.spellPut("zan", -11045);
		ChineseConverter.spellPut("zang", -11041);
		ChineseConverter.spellPut("zao", -11038);
		ChineseConverter.spellPut("ze", -11024);
		ChineseConverter.spellPut("zei", -11020);
		ChineseConverter.spellPut("zen", -11019);
		ChineseConverter.spellPut("zeng", -11018);
		ChineseConverter.spellPut("zha", -11014);
		ChineseConverter.spellPut("zhai", -10838);
		ChineseConverter.spellPut("zhan", -10832);
		ChineseConverter.spellPut("zhang", -10815);
		ChineseConverter.spellPut("zhao", -10800);
		ChineseConverter.spellPut("zhe", -10790);
		ChineseConverter.spellPut("zhen", -10780);
		ChineseConverter.spellPut("zheng", -10764);
		ChineseConverter.spellPut("zhi", -10587);
		ChineseConverter.spellPut("zhong", -10544);
		ChineseConverter.spellPut("zhou", -10533);
		ChineseConverter.spellPut("zhu", -10519);
		ChineseConverter.spellPut("zhua", -10331);
		ChineseConverter.spellPut("zhuai", -10329);
		ChineseConverter.spellPut("zhuan", -10328);
		ChineseConverter.spellPut("zhuang", -10322);
		ChineseConverter.spellPut("zhui", -10315);
		ChineseConverter.spellPut("zhun", -10309);
		ChineseConverter.spellPut("zhuo", -10307);
		ChineseConverter.spellPut("zi", -10296);
		ChineseConverter.spellPut("zong", -10281);
		ChineseConverter.spellPut("zou", -10274);
		ChineseConverter.spellPut("zu", -10270);
		ChineseConverter.spellPut("zuan", -10262);
		ChineseConverter.spellPut("zui", -10260);
		ChineseConverter.spellPut("zun", -10256);
		ChineseConverter.spellPut("zuo", -10254);
	}

	private static void spellPut(final String spell, final int ascii) {

		ChineseConverter.spellMap.put(spell, new Integer(ascii));
	}

	/**
	 * 获得单个汉字的Ascii.
	 *
	 * @param cn
	 *            char 汉字字符
	 * @return int 错误返回 0, 否则返回ascii
	 */
	public static int getCnAscii(final char cn) {

		final byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // 错误
			return 0;
		}
		if (bytes.length == 1) { // 英文字符
			return bytes[0];
		}
		if (bytes.length == 2) { // 中文字符
			final int hightByte = 256 + bytes[0];
			final int lowByte = 256 + bytes[1];
			final int ascii = (256 * hightByte + lowByte) - 256 * 256;

			return ascii;
		}

		return 0; // 错误
	}

	/**
	 * 根据ASCII码到SpellMap中查找对应的拼音
	 *
	 * @param ascii
	 *            int 字符对应的ASCII
	 * @return String 拼音,首先判断ASCII是否>0&<160,如果是返回对应的字符,
	 *         否则到SpellMap中查找,如果没有找到拼音,则返回null,如果找到则返回拼音.
	 */
	private static String getSpellByAscii(final int ascii) {

		if (ascii > 0 && ascii < 160) { // 单字符
			return String.valueOf((char) ascii);
		}

		if (ascii < -20319 || ascii > -10247) { // 不知道的字符
			return null;
		}

		final Set keySet = ChineseConverter.spellMap.keySet();
		final Iterator it = keySet.iterator();

		String spell0 = null;

		String spell = null;

		int asciiRang0 = -20319;
		int asciiRang;
		while (it.hasNext()) {

			spell = (String) it.next();
			final Object valObj = ChineseConverter.spellMap.get(spell);
			if (valObj instanceof Integer) {
				asciiRang = ((Integer) valObj).intValue();

				if (ascii >= asciiRang0 && ascii < asciiRang) { // 区间找到
					return (spell0 == null) ? spell : spell0;
				} else {
					spell0 = spell;
					asciiRang0 = asciiRang;
				}
			}
		}

		return null;

	}

	/**
	 * 返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换。
	 * <p>
	 * 比如：中软恒信=ZHONGRUANHENXIN
	 *
	 * @param cnStr
	 *            String 字符串
	 * @return String 转换成全拼后的字符串
	 */
	public static String getFullSpell(final String cnStr) {

		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}

		final char[] chars = cnStr.toCharArray();
		final StringBuffer retuBuf = new StringBuffer();
		for (final char c : chars) {
			final int ascii = ChineseConverter.getCnAscii(c);
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(c);
			} else {
				final String spell = ChineseConverter.getSpellByAscii(ascii);
				if (spell == null) {
					continue;
				} else {
					retuBuf.append(spell.toUpperCase());
				}
			}
		}

		return retuBuf.toString();
	}

	/**
	 * 获取一个中文字符串的首写字母的字符串，用大写字母表示。
	 * <p>
	 * 比如：中华人民共和国=ZHRMGHG
	 *
	 * @param cnStr
	 * @return
	 */
	public static String getFirstSpell(final String cnStr) {

		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}

		final char[] chars = cnStr.toCharArray();
		final StringBuffer retuBuf = new StringBuffer();
		for (final char c : chars) {
			final int ascii = ChineseConverter.getCnAscii(c);
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(c);
			} else {
				final String spell = ChineseConverter.getSpellByAscii(ascii);
				if (spell == null) {
					continue;
				} else {
					retuBuf.append(spell.substring(0, 1).toUpperCase());
				}
			}
		}

		return retuBuf.toString();
	}

	private static String positiveIntegerToHanStr(final String NumStr) {

		// 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15) {
			return "数值过大!";
		}
		for (int i = len - 1; i >= 0; i--)
		{
			if (NumStr.charAt(len - i - 1) == ' ') {
				continue;
			}
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9) {
				return "输入含非数字字符!";
			}

			if (n != 0) {
				if (lastzero)
				{
					RMBStr += ChineseConverter.HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				}
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) // 如十进位前有零也不发壹音用此行
				// if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
				RMBStr += ChineseConverter.HanDigiStr[n];
				RMBStr += ChineseConverter.HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记
			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue))
				{
					RMBStr += ChineseConverter.HanDiviStr[i]; // “亿”或“万”
				}
			}
			if (i % 8 == 0)
			{
				hasvalue = false; // 万进位前有值标记逢亿复位
			}
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
		{
			return ChineseConverter.HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		}
		return RMBStr;
	}

	/**
	 * 将数值转成中文表示的钱数。
	 *
	 * @param val
	 *            钱数。
	 * @param flag
	 *            转换字符串前缀。比如"￥"之类的字符。
	 * @param flagKind
	 *            转换字符串前缀。比如"元"。
	 * @return 转换后的字符串。
	 */
	public static String numToRMBStr(double val, final String flag, final String flagKind) {

		String signStr = "";
		String tailStr = "";

		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			signStr = "负";
		}

		if (val > 99999999999999.999 || val < -99999999999999.999) {
			return "数值位数过大!";
		}
		// 四舍五入到分
		final long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0)
		{
			tailStr = "整";
		} else {
			tailStr = ChineseConverter.HanDigiStr[jiao];
			if (jiao != 0) {
				tailStr += "角";
			}
			if (integer == 0 && jiao == 0) {
				tailStr = "";
			}
			if (fen != 0) {
				tailStr += ChineseConverter.HanDigiStr[fen] + "分";
			} else {
				tailStr += "整";
			}
		}

		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;
		if (flag != null && flagKind != null) {
			return flag + signStr + ChineseConverter.positiveIntegerToHanStr(String.valueOf(integer)) + flagKind
					+ tailStr;
		} else {
			return signStr + ChineseConverter.positiveIntegerToHanStr(String.valueOf(integer)) + "元" + tailStr;
		}
	}
}
