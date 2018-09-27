import jieba  
import jieba.analyse


def stopwordslist(filepath):  
    stopwords = [line.strip() for line in open(filepath, 'r', encoding='utf-8').readlines()]  
    return stopwords  
 
 
def keywordbytfide(words):
    list=[]
    jieba.analyse.set_idf_path('D:\\study\\大三下\\数据挖掘竞赛\\jieba语料\\keyword')
#     jieba.analyse.set_idf_path(idfpath)
    for x, w in jieba.analyse.extract_tags(words, withWeight=True):
            list.append((x,w))
    return str(list[0][0]) 
 
def seg_sentence(sentence):  
    sentence_seged = jieba.cut(sentence.strip())  
    stopwords = stopwordslist('D:\study\大三下\数据挖掘竞赛\jieba语料\stopword')  # 杩欓噷鍔犺浇鍋滅敤璇嶇殑璺緞  
    outstr = ''  
    for word in sentence_seged:  
        if word not in stopwords:  
            if word != '\t':  
                outstr += word  
                outstr += " "  
    return outstr  


inputs = open('D:\study\大三下\软件创新俱乐部\文本.txt', 'r', encoding='utf-8')  
out=open('D:\study\大三下\软件创新俱乐部\result.txt', 'a+', encoding='utf-8')  
for line in inputs:  
    line_seg=seg_sentence(line.strip())
    print(line_seg)
    print(keywordbytfide(line_seg))
    out.write(line_seg)
out.close()
inputs.close()  