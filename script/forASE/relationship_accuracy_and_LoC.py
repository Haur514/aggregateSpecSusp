import pandas as pd
import numpy as np
import sys
import matplotlib.pyplot as plt
import matplotlib
import glob
import os
import seaborn as sns

######################################################
# 変数 実験を回す際に指定してください
######################################################
proximity_type = "jaccard"
fl_formula = "Dstar5"
similarity_calculation_formula = "Haka"
experimental_subject = "math"
######################################################


#######################################################
# グローバル変数
#######################################################
home_dir = "/home/h-yosiok/Lab/2022/aggregateSpecSusp/"
result_data_dir = home_dir+proximity_type+"/" + \
    fl_formula+"/"+similarity_calculation_formula+"/"
number_of_bsbfl_win = 0
number_of_bsbfl_lose = 0
number_of_statement_executed_when_bsbfl_win = 0
number_of_statement_executed_when_bsbfl_lose = 0

# 散布図を作成する際に使用するリスト
diff_accuracy_bsbfl_win_nonbsbfl = list()
diff_accuracy_bsbfl_lose_nonbsbfl = list()
diff_statement_bsbfl_win_nonbsbfl = list()
diff_statement_bsbfl_lose_nonbsbfl = list()
#######################################################


# ファイルを読み込み
def read_file(result_data_file):
    result_data_list = pd.read_csv(result_data_file).values.tolist()
    analyze_from_excused_statement(result_data_list)

# csvファイルの一行分を配列で受け取り，BSBFLとNonBSBFLにおいて欠陥箇所の順位に差異が生じた部分における，失敗テストが通過したプログラム文の数を分析する.


def analyze_from_excused_statement(result_data_list):
    global number_of_bsbfl_lose
    global number_of_bsbfl_win
    global number_of_statement_executed_when_bsbfl_lose
    global number_of_statement_executed_when_bsbfl_win
    global diff_accuracy_bsbfl_win_nonbsbfl
    global diff_accuracy_bsbfl_lose_nonbsbfl
    global diff_statement_bsbfl_win_nonbsbfl
    global diff_statement_bsbfl_lose_nonbsbfl
    for result_data in result_data_list:
        if is_BSBFL_win_NonBSBFL(result_data):
            number_of_bsbfl_win += 1
            number_of_statement_executed_when_bsbfl_win += result_data[5]
            diff_accuracy_bsbfl_win_nonbsbfl.append(
                (result_data[2] - result_data[3])/result_data[5])
            diff_statement_bsbfl_win_nonbsbfl.append(result_data[5])
        if is_BSBFL_lose_NonBSBFL(result_data):
            number_of_bsbfl_lose += 1
            number_of_statement_executed_when_bsbfl_lose += result_data[5]
            diff_accuracy_bsbfl_lose_nonbsbfl.append(
                (result_data[2] - result_data[3])/result_data[5])
            diff_statement_bsbfl_lose_nonbsbfl.append(result_data[5])


def is_BSBFL_win_NonBSBFL(result_data):
    if result_data[3] < result_data[2]:
        return True
    else:
        return False


def is_BSBFL_lose_NonBSBFL(result_data):
    if result_data[3] > result_data[2]:
        return True
    else:
        return False


def get_average_number_of_statement_bsbfl_win():
    if number_of_bsbfl_win == 0:
        if number_of_statement_executed_when_bsbfl_win == 0:
            return 0
        else:
            print("ERROR")
            return -1
    return number_of_statement_executed_when_bsbfl_win / number_of_bsbfl_win


def get_average_number_of_statement_bsbfl_lose():
    if number_of_bsbfl_lose == 0:
        if number_of_statement_executed_when_bsbfl_lose == 0:
            return 0
        else:
            print("ERROR")
            return -1
    return number_of_statement_executed_when_bsbfl_lose / number_of_bsbfl_lose

#######################################################
# 散布図を作成します．
#######################################################


def make_scatter():
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.scatter(diff_statement_bsbfl_win_nonbsbfl, diff_accuracy_bsbfl_win_nonbsbfl,
               linewidths=2, c='#aaaaFF', edgecolors='b')
    ax.scatter(diff_statement_bsbfl_lose_nonbsbfl,
               diff_accuracy_bsbfl_lose_nonbsbfl, linewidths=2, c='#FFaaaa', edgecolors='r')
    plt.show()


#######################################################
# 閾値ごとの各対象プロジェクトにおけるTopN%を出力
#######################################################
def result_per_threshold_of_all(result_data_file):
    result_data_list = pd.read_csv(result_data_file).values.tolist()
    average_sbfl = 0.0
    average_bsbfl = 0.0
    average_nonbsbfl = 0.0
    for result_data in result_data_list:
        average_sbfl += result_data[1]/result_data[5]
        average_bsbfl += result_data[3]/result_data[5]
        average_nonbsbfl += result_data[2]/result_data[5]
    average_sbfl /= len(result_data_list)
    average_bsbfl /= len(result_data_list)
    average_nonbsbfl /= len(result_data_list)
    print(str(average_sbfl) + " " +
          str(average_nonbsbfl) + " " + str(average_bsbfl))


#######################################################
# 各閾値ごとの各プロジェクトにおけるTopN%を棒グラフで出力
#######################################################
def make_line_graph():
    thsld = list()
    for i in range(1, 20):
        thsld.append(str(round(i*0.05, 2)))
    sbfl_math_topn = list()
    bsbfl_math_topn = list()
    nonbsbfl_math_topn = list()
    sbfl_lang_topn = list()
    bsbfl_lang_topn = list()
    nonbsbfl_lang_topn = list()
    sbfl_time_topn = list()
    bsbfl_time_topn = list()
    nonbsbfl_time_topn = list()
    for threshold in thsld:
        # LANG
        if not os.path.exists(result_data_dir+"lang"+threshold+".csv"):
            sbfl_lang_topn.append(np.NaN)
            bsbfl_lang_topn.append(np.NaN)
            nonbsbfl_lang_topn.append(np.NaN)
            continue
        sbfl_lang_topn.append(get_topn_of_sbfl(
            result_data_dir+"lang"+threshold+".csv")[0])
        bsbfl_lang_topn.append(get_topn_of_sbfl(
            result_data_dir+"lang"+threshold+".csv")[1])
        nonbsbfl_lang_topn.append(get_topn_of_sbfl(
            result_data_dir+"lang"+threshold+".csv")[2])
    for threshold in thsld:
        # TIME
        if not os.path.exists(result_data_dir+"time"+threshold+".csv"):
            sbfl_time_topn.append(np.NaN)
            bsbfl_time_topn.append(np.NaN)
            nonbsbfl_time_topn.append(np.NaN)
            continue
        sbfl_time_topn.append(get_topn_of_sbfl(
            result_data_dir+"time"+threshold+".csv")[0])
        bsbfl_time_topn.append(get_topn_of_sbfl(
            result_data_dir+"time"+threshold+".csv")[1])
        nonbsbfl_time_topn.append(get_topn_of_sbfl(
            result_data_dir+"time"+threshold+".csv")[2])
    for threshold in thsld:
        # MATH
        if not os.path.exists(result_data_dir+"math"+threshold+".csv"):
            sbfl_math_topn.append(np.NaN)
            bsbfl_math_topn.append(np.NaN)
            nonbsbfl_math_topn.append(np.NaN)
            continue
        sbfl_math_topn.append(get_topn_of_sbfl(
            result_data_dir+"math"+threshold+".csv")[0])
        bsbfl_math_topn.append(get_topn_of_sbfl(
            result_data_dir+"math"+threshold+".csv")[1])
        nonbsbfl_math_topn.append(get_topn_of_sbfl(
            result_data_dir+"math"+threshold+".csv")[2])
    plt.figure(figsize=(7, 4), dpi=150)
    plt.plot(thsld, sbfl_time_topn, label="sbfl")
    plt.plot(thsld, bsbfl_time_topn, label="bsbfl")
    plt.plot(thsld, nonbsbfl_time_topn, label="nonbsbfl")
    plt.grid(True)
    plt.xlabel("threshold")
    plt.ylabel("TopN%")
    plt.title("Time")
    plt.legend()
    plt.savefig("line_graph_time.pdf")
    plt.cla()

    plt.figure(figsize=(7, 4), dpi=150)
    plt.plot(thsld, sbfl_lang_topn, label="sbfl")
    plt.plot(thsld, bsbfl_lang_topn, label="bsbfl")
    plt.plot(thsld, nonbsbfl_lang_topn, label="nonbsbfl")
    plt.grid(True)
    plt.xlabel("threshold")
    plt.ylabel("TopN%")
    plt.title("Lang")
    plt.legend()
    plt.savefig("line_graph_lang.pdf")
    plt.cla()

    plt.plot(thsld, sbfl_math_topn, label="sbfl")
    plt.plot(thsld, bsbfl_math_topn, label="bsbfl")
    plt.plot(thsld, nonbsbfl_math_topn, label="nonbsbfl")
    plt.grid(True)
    plt.xlabel("threshold")
    plt.ylabel("TopN%")
    plt.title("Math")
    plt.legend()
    plt.savefig("line_graph_math.pdf")


def get_topn_of_sbfl(result_data_file):
    result_data_list = pd.read_csv(result_data_file).values.tolist()
    average_sbfl = 0.0
    average_bsbfl = 0.0
    average_nonbsbfl = 0.0
    for result_data in result_data_list:
        average_sbfl += result_data[1]/result_data[5]
        average_bsbfl += result_data[3]/result_data[5]
        average_nonbsbfl += result_data[2]/result_data[5]
    average_sbfl /= len(result_data_list)
    average_bsbfl /= len(result_data_list)
    average_nonbsbfl /= len(result_data_list)
    ret = list()
    ret.append(average_sbfl)
    ret.append(average_bsbfl)
    ret.append(average_nonbsbfl)
    return ret


#######################################################
# SBFL,BSBFL,NonBSBFLにおける各実験対象でのTopN%の
# 箱ひげ図を作成する．
#######################################################
def make_box_plot():
    thsld = list()
    for i in range(1, 20):
        thsld.append(str(round(i*0.05, 2)))

    experimental_subject = "time"
    plt.boxplot([get_all_topN(result_data_dir+experimental_subject+"0.85.csv")[0],get_all_topN(result_data_dir+experimental_subject+"0.95.csv")[1],get_all_topN(result_data_dir+experimental_subject+"0.85.csv")[2]],labels=['SBFL','NonBSBFL',"BSBFL"],whis=[0,100])
    plt.savefig("box_plot_time.pdf")
    plt.cla()
    
    experimental_subject = "lang"
    plt.boxplot([get_all_topN(result_data_dir+experimental_subject+"0.85.csv")[0],get_all_topN(result_data_dir+experimental_subject+"0.45.csv")[1],get_all_topN(result_data_dir+experimental_subject+"0.45.csv")[2]],labels=['SBFL','NonBSBFL',"BSBFL"],whis=[0,100])
    plt.savefig("box_plot_lang.pdf")
    plt.cla()
    
    experimental_subject = "math"
    plt.boxplot([get_all_topN(result_data_dir+experimental_subject+"0.85.csv")[0],get_all_topN(result_data_dir+experimental_subject+"0.9.csv")[1],get_all_topN(result_data_dir+experimental_subject+"0.85.csv")[2]],labels=['SBFL','NonBSBFL',"BSBFL"],whis=[0,100])
    plt.savefig("box_plot_math.pdf")
    plt.cla()

# すべての手法におけるtopN%のリストを
# [sbfl.list(),nonbsbfl.list(),bsbfl.list()]
# のリストにして返す．
def get_all_topN(file_data_file):
    result_data_list = pd.read_csv(file_data_file).values.tolist()
    sbfl_result = list()
    nonbsbfl_result = list()
    bsbfl_result = list()
    for result_data in result_data_list:
        sbfl_result.append(result_data[1]/result_data[5])
        nonbsbfl_result.append(result_data[2]/result_data[5])
        bsbfl_result.append(result_data[3]/result_data[5])
    return [sbfl_result, nonbsbfl_result, bsbfl_result]


#######################################################
# 重み付け無しとBSBFLで順位に差異が出た部分での
# TopN% の値を比較します.
# 引数 : ファイルのpath
# 出力 : 順位に違いが出た部分でのTopN%
#######################################################
def print_topn_diff_sbfl_bsbfl():
    thsld = list()
    for i in range(1, 20):
        thsld.append(str(round(i*0.05, 2)))
    # TIME
    ex_subject = ["lang","time","math"]
    for subject in ex_subject:
        print(subject)
        for threshold in thsld:
            result_data_file = result_data_dir+subject+threshold+".csv"
            if not os.path.exists(result_data_file):
                continue
            print(threshold + " " + str(get_topn_diff_sbfl_bsbfl(result_data_file)
                [0]) + " " + str(get_topn_diff_sbfl_bsbfl(result_data_file)[1]))
    for subject in ex_subject:
        print(subject)
        for threshold in thsld:
            result_data_file = result_data_dir+subject+threshold+".csv"
            if not os.path.exists(result_data_file):
                continue
            print(threshold + " " + str(get_number_bsbfl_win_or_lose_sbfl(result_data_file)
                [0]) + " " + str(get_number_bsbfl_win_or_lose_sbfl(result_data_file)[1]))


def get_topn_diff_sbfl_bsbfl(result_data_file):
    result_data_list = pd.read_csv(result_data_file).values.tolist()
    sbfl_average_topn = 0.0
    bsbfl_average_topn = 0.0
    number_of_rank_diff_sbfl_and_bsbfl = 0
    for result_data in result_data_list:
        if result_data[1] == result_data[3]:
            continue
        sbfl_average_topn += result_data[1]/result_data[5]
        bsbfl_average_topn += result_data[3]/result_data[5]
        number_of_rank_diff_sbfl_and_bsbfl += 1
    sbfl_average_topn /= number_of_rank_diff_sbfl_and_bsbfl
    bsbfl_average_topn /= number_of_rank_diff_sbfl_and_bsbfl
    return [sbfl_average_topn, bsbfl_average_topn]

# BSBFLがSBFLに勝った/負けた欠陥箇所の個数を返す．
def get_number_bsbfl_win_or_lose_sbfl(result_data_file):
    result_data_list = pd.read_csv(result_data_file).values.tolist()
    number_bsbfl_win_sbfl = 0
    number_bsbfl_lose_sbfl = 0
    for result_data in result_data_list:
        if result_data[1] == result_data[3]:
            continue
        if result_data[1] < result_data[3]:
            number_bsbfl_lose_sbfl += 1
        elif result_data[1] > result_data[3]:
            number_bsbfl_win_sbfl += 1
    return [number_bsbfl_win_sbfl,number_bsbfl_lose_sbfl]


#######################################################
# 最も良い結果を取る時の閾値での各種法を比較
#######################################################
def print_best_bsbfl_win_best_nonbsbfl():
    lang_best_bsbfl_file = result_data_dir+"lang0.4.csv"
    lang_best_nonbsbfl_file = result_data_dir+"lang0.45.csv"
    math_best_bsbfl_file = result_data_dir+"math0.85.csv"
    math_best_nonbsbfl_file = result_data_dir+"math0.9.csv"
    time_best_bsbfl_file = result_data_dir+"time0.85.csv"
    time_best_nonbsbfl_file = result_data_dir+"time0.95.csv"
    lang_best_bsbfl_list = get_all_topN(lang_best_bsbfl_file)[2]
    lang_best_nonbsbfl_list = get_all_topN(lang_best_nonbsbfl_file)[1]
    math_best_bsbfl_list = get_all_topN(math_best_bsbfl_file)[2]
    math_best_nonbsbfl_list = get_all_topN(math_best_nonbsbfl_file)[1]
    time_best_bsbfl_list = get_all_topN(time_best_bsbfl_file)[2]
    time_best_nonbsbfl_list = get_all_topN(time_best_nonbsbfl_file)[1]
    print(countup_bsbfl_win_nonbsbfl(lang_best_bsbfl_list,lang_best_nonbsbfl_list))
    print(countup_bsbfl_win_nonbsbfl(time_best_bsbfl_list,time_best_nonbsbfl_list))
    print(countup_bsbfl_win_nonbsbfl(math_best_bsbfl_list,math_best_nonbsbfl_list))
            
def countup_bsbfl_win_nonbsbfl(bsbfl_list,nonbsbfl_list):
    bsbfl_win = 0
    nonbsbfl_win = 0
    for i in range(0,len(bsbfl_list)):
        if bsbfl_list[i] < nonbsbfl_list[i]:
            bsbfl_win += 1
        elif bsbfl_list[i] > nonbsbfl_list[i]:
            nonbsbfl_win += 1
    return [bsbfl_win,nonbsbfl_win]
#######################################################
# MAIN
#######################################################

# 散布図作成する
# for threshold in ["0.6","0.65","0.7","0.75","0.8","0.85","0.9","0.95"]:
#     if not os.path.exists(result_data_dir+experimental_subject+threshold+".csv"):
#         continue
#     print(threshold)
#     result_per_threshold_of_all(result_data_dir+experimental_subject+threshold+".csv")
# make_scatter()

# # 折れ線グラフ作成
make_line_graph()

# # 箱ひげ図を作成する.
# make_box_plot()

# # BSBFLとNonBSBFLで違いが生じた部分での個数を出力
# print_best_bsbfl_win_best_nonbsbfl()