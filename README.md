# baidu-tran-sdk
一个简单百度翻译封装。提供了语种识别、文本翻译、语音翻译、图片翻译

## baidu-tran-tool
主要实现语种识别、文本翻译、语音翻译、图片翻译的API调用
### Translatlor(appId, secretKey)
  - 构造方法
    + appId 百度翻译appId
    + secretKey 百度翻译密钥
### Translator.textTran()
    public <T extends AbstractTranslation<?>> T textTran(
            String query,
            String from,
            String to,
            Class<T> clazz
    ) 
   - 文本翻译方法
      + query 需要翻译的内容
      + from 原文语言类型，具体看百度翻译开发文档
      + to 译文语言类型，......
      + clazz 翻译结果处理类。有默认处理的方法
### Translator.voiceTran()
    public <T extends AbstractTranslation<?>> T voiceTran(
            byte[] voice,
            String from,
            String to,
            String format,
            Class<T> clazz
    ) 
   - 语言翻译方法
      + voice 语音数据
      + from 原文语言
      + to 目标语言
      + formt 语音编码
        - pcm（无损音频格式）：也称为 raw 格式。音频输入最原始的格式，不用解码。
        - wav（无损音频格式，pcm 编码）：在 pcm 格式的开头额外包含一段描述采样率、编码等信息的编码。
        - amr（有损压缩格式）：对音频数据进行有损压缩，类似 mp3 文件。
        - m4a（有损压缩格式，AAC 编码）：对音频数据进行有损压缩，通常仅供微信小程序使用的格式。
        - 由于底层识别使用的是 pcm 格式，因此推荐直接上传 pcm 格式。若上传其它格式，在服务器端会有额外转换为 pcm 格式的工作，从而增加一定的调用耗时。
      + clazz 翻译结果处理类
### Translator.photoTran()
     public <T extends AbstractTranslation<?>> T  photoTran(
            byte[] image,
            String suffix,
            String from,
            String to,
            Class<T> clazz
    ) 
  - 图片翻译方法
      + image 图片数据
      + suffix 图片格式后缀
      + from 原文语言
      + to 目标语言
      + clazz 翻译结果处理类
### Translator.langIdent()
     public <T extends AbstractTranslation<?>> T langIdent(
            String query,
            Class<T> clazz
    )
   - 语种识别方法
      + query 文本
      + clazz 翻译结果处理类
    

  
