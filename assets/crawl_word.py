from urllib import request
import urllib
import json
import pymysql

conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', passwd='maxinyin', db='vocabulary', charset='utf8')
cursor = conn.cursor()

word_array = ['now and then']
# get word from database
sql = 'select word from cet4 union select word from cet6 union select word from toefl'
cursor.execute(sql)
results = cursor.fetchall()
for row in results:
	word_array.append(row[0])



j = 0
while j < len(word_array):
	print(j)
	word = word_array[j]
	j = j+1
	file = urllib.request.urlopen("http://xtk.azurewebsites.net/BingDictService.aspx?Word=%s" % word.replace(' ', '-'))
	page = file.read()
	page = page.decode('utf-8')
	json_res = json.loads(page)
	def_form = ['','','','']
	def_mean = ['','','','']
	pron = pron_link = sample_eng = sample_chn  =sample_link = ''
	if json_res['pronunciation'] == None:
		[pron, pron_link] = ['','']
	else:
		[pron, pron_link] = [json_res['pronunciation']['AmE'],  json_res['pronunciation']['AmEmp3']]


	l = len(json_res['defs'])
	l = l - 1
	if l > 4 :
		l = 4
	for i in range(l):
		def_form[i] = json_res['defs'][i]['pos']
		def_mean[i] = json_res['defs'][i]['def']

	if json_res['sams'] == None:
		[sample_eng, sample_chn, sample_link] = ['','','']
	else:
		[sample_eng, sample_chn, sample_link] = [json_res['sams'][0]['eng'],json_res['sams'][0]['chn'], json_res['sams'][0]['mp3Url']]

	sql = "insert into word_info (word, pron, pronlink, def_form_1, def_mean_1, def_form_2, def_mean_2, def_form_3, def_mean_3,\
def_form_4, def_mean_4,sample_eng, sample_chn, sample_link) values (\
\"%s\", \"%s\", \"%s\", \"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",'%s', '%s', \"%s\")" % (word, pron, pron_link, def_form[0], def_mean[0], \
def_form[1], def_mean[1],def_form[2], def_mean[2], def_form[3], def_mean[3], pymysql.escape_string(sample_eng), sample_chn, sample_link)
	print(sql);
	cursor.execute(sql);
	conn.commit()
	

conn.close()



