package blackbird.com.recyclerviewdemo.uitls

import java.io.UnsupportedEncodingException

/**
 * Created by yzzhang on 2017/12/9.
 */
class StringUtils private constructor() {
    companion object {
        /**
         * 判断字符串是否为null或长度为0
         *
         * @param s 待校验字符串
         * @return `true`: 空<br></br> `false`: 不为空
         */
        @JvmStatic
        fun isEmpty(s: CharSequence?): Boolean {
            return s == null || s.length == 0
        }

        @JvmStatic
        fun isNotEmpty(s: CharSequence?): Boolean {
            return !isEmpty(s)
        }

        /**
         * 判断字符串是否为null或全为空格
         *
         * @param s 待校验字符串
         * @return `true`: null或全空格<br></br> `false`: 不为null且不全空格
         */
        fun isSpace(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' }.length == 0
        }

        /**
         * 判断两字符串是否相等
         *
         * @param a 待校验字符串a
         * @param b 待校验字符串b
         * @return `true`: 相等<br></br>`false`: 不相等
         */
        @JvmStatic
        fun equals(a: CharSequence?, b: CharSequence?): Boolean {
            if (a === b) return true
            var length: Int=0
            return if (a != null && b != null && a.length.also { length = it } == b.length) {
                if (a is String && b is String) {
                    a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    true
                }
            } else false
        }

        /**
         * null转为长度为0的字符串
         *
         * @param s 待转字符串
         * @return s为null转为长度为0字符串，否则不改变
         */
        fun null2Length0(s: String?): String {
            return s ?: ""
        }

        /**
         * 返回字符串长度
         *
         * @param s 字符串
         * @return null返回0，其他返回自身长度
         */
        fun length(s: CharSequence?): Int {
            return s?.length ?: 0
        }

        /**
         * 首字母大写
         *
         * @param s 待转字符串
         * @return 首字母大写字符串
         */
    /*    fun upperFirstLetter(s: String): String {
            if (isEmpty(s) || !Character.isLowerCase(s[0])) {
                return s
            }
            return (s[0].toInt() - 32) as Char.toString() + s.substring(1)
        }*/

        /**
         * 首字母小写
         *
         * @param s 待转字符串
         * @return 首字母小写字符串
         */
        /*fun lowerFirstLetter(s: String): String {
            if (isEmpty(s) || !Character.isUpperCase(s[0])) {
                return s
            }
            return (s[0].toInt() + 32) as Char.toString() + s.substring(1)
        }*/

        /**
         * 反转字符串
         *
         * @param s 待反转字符串
         * @return 反转字符串
         */
        fun reverse(s: String): String {
            val len = length(s)
            if (len <= 1) return s
            val mid = len shr 1
            val chars = s.toCharArray()
            var c: Char
            for (i in 0 until mid) {
                c = chars[i]
                chars[i] = chars[len - i - 1]
                chars[len - i - 1] = c
            }
            return String(chars)
        }

        /**
         * 转化为半角字符
         *
         * @param s 待转字符串
         * @return 半角字符串
         */
      /*  fun toDBC(s: String): String {
            if (isEmpty(s)) {
                return s
            }
            val chars = s.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i] == 12288) {
                    chars[i] = ' '
                } else if (65281 <= chars[i] && chars[i] <= 65374) {
                    chars[i] = (chars[i] - 65248)
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }*/

        /**
         * 转化为全角字符
         *
         * @param s 待转字符串
         * @return 全角字符串
         */
    /*    fun toSBC(s: String): String {
            if (isEmpty(s)) {
                return s
            }
            val chars = s.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i] == ' ') {
                    chars[i] = 12288.toChar()
                } else if (33 <= chars[i] && chars[i] <= 126) {
                    chars[i] = (chars[i] + 65248)
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }*/

        private val pyValue = intArrayOf(-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242,
                -20230, -20051, -20036, -20032,
                -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751,
                -19746, -19741, -19739, -19728,
                -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281,
                -19275, -19270, -19263, -19261,
                -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006,
                -19003, -18996, -18977, -18961,
                -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696,
                -18526, -18518, -18501, -18490,
                -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183,
                -18181, -18012, -17997, -17988,
                -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721,
                -17703, -17701, -17697, -17692,
                -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983,
                -16970, -16942, -16915, -16733,
                -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433,
                -16429, -16427, -16423, -16419,
                -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171,
                -16169, -16158, -16155, -15959,
                -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661,
                -15659, -15652, -15640, -15631,
                -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369,
                -15363, -15362, -15183, -15180,
                -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119,
                -15117, -15110, -15109, -14941,
                -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889,
                -14882, -14873, -14871, -14857,
                -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384,
                -14379, -14368, -14355, -14353,
                -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112,
                -14109, -14099, -14097, -14094,
                -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878,
                -13870, -13859, -13847, -13831,
                -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359,
                -13356, -13343, -13340, -13329,
                -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060,
                -12888, -12875, -12871, -12860,
                -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556,
                -12359, -12346, -12320, -12300,
                -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781,
                -11604, -11589, -11536, -11358,
                -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024,
                -11020, -11019, -11018, -11014,
                -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329,
                -10328, -10322, -10315, -10309,
                -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254)
        private val pyStr = arrayOf("a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao",
                "bei", "ben", "beng", "bi", "bian",
                "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai",
                "chan", "chang", "chao", "che",
                "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci",
                "cong", "cou", "cu", "cuan",
                "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die",
                "ding", "diu", "dong", "dou", "du",
                "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou",
                "fu", "ga", "gai", "gan", "gang",
                "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun",
                "guo", "ha", "hai", "han", "hang",
                "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun",
                "huo", "ji", "jia", "jian",
                "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan",
                "kang", "kao", "ke", "ken",
                "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan",
                "lang", "lao", "le", "lei", "leng",
                "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan",
                "lue", "lun", "luo", "ma", "mai",
                "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu",
                "mo", "mou", "mu", "na", "nai",
                "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning",
                "niu", "nong", "nu", "nv", "nuan",
                "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao",
                "pie", "pin", "ping", "po", "pu",
                "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun",
                "ran", "rang", "rao", "re",
                "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao",
                "se", "sen", "seng", "sha",
                "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan",
                "shuang", "shui", "shun",
                "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te",
                "teng", "ti", "tian", "tiao",
                "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei",
                "wen", "weng", "wo", "wu", "xi",
                "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",
                "yan", "yang", "yao", "ye", "yi",
                "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze",
                "zei", "zen", "zeng", "zha",
                "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai",
                "zhuan", "zhuang", "zhui",
                "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo")

        /**
         * 单个汉字转成ASCII码
         *
         * @param s 单个汉字字符串
         * @return 如果字符串长度是1返回的是对应的ascii码，否则返回-1
         */
        private fun oneCn2ASCII(s: String): Int {
            if (s.length != 1) return -1
            var ascii = 0
            try {
                val bytes = s.toByteArray(charset("GB2312"))
                ascii = if (bytes.size == 1) {
                    bytes[0].toInt()
                } else if (bytes.size == 2) {
                    val highByte = 256 + bytes[0]
                    val lowByte = 256 + bytes[1]
                    256 * highByte + lowByte - 256 * 256
                } else {
                    throw IllegalArgumentException("Illegal resource string")
                }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            return ascii
        }

        /**
         * 单个汉字转成拼音
         *
         * @param s 单个汉字字符串
         * @return 如果字符串长度是1返回的是对应的拼音，否则返回`null`
         */
        private fun oneCn2PY(s: String): String? {
            val ascii = oneCn2ASCII(s)
            if (ascii == -1) return null
            var ret: String? = null
            if (0 <= ascii && ascii <= 127) {
               // ret = ascii as Char.toString()
                //ret = ascii as Char.toString()
            } else {
                for (i in pyValue.indices.reversed()) {
                    if (pyValue[i] <= ascii) {
                        ret = pyStr[i]
                        break
                    }
                }
            }
            return ret
        }

        /**
         * 获得第一个汉字首字母
         *
         * @param s 单个汉字字符串
         * @return 拼音
         */
        fun getPYFirstLetter(s: String): String? {
            if (isSpace(s)) return ""
            val first: String
            val py: String?
            first = s.substring(0, 1)
            py = oneCn2PY(first)
            return py?.substring(0, 1)
        }

        /**
         * 中文转拼音
         *
         * @param s 汉字字符串
         * @return 拼音
         */
        fun cn2PY(s: String): String {
            var hz: String
            var py: String?
            val sb = StringBuilder()
            for (i in 0 until s.length) {
                hz = s.substring(i, i + 1)
                py = oneCn2PY(hz)
                if (py == null) {
                    py = "?"
                }
                sb.append(py)
            }
            return sb.toString()
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}