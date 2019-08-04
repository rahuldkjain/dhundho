import json
import os
import requests
from flask import Flask
from flask import Flask, request
import nltk
import requests
from spellchecker import SpellChecker
from nltk.corpus import stopwords
import string
app = Flask(__name__)

comps = {"title", "brand", "category", "subcategory", "model_number", "bundle_subcategory", "bundle_category",
         "model_no", "rank", "product_type", "attributes", "description", "shipping_details", "stock", "color", "size",
         "all_sizes", "features", "product_features", "prod_features", "no_reviews", "no_ratings", "avg_rating",
         "final_url", "images", "image_temp", "thumbnail", "warranty", "condition", "product_condition", "url",
         "canonical_url", "mrp", "available_price", "shipping_price"}

@app.route('/upload_fashion')
def upload_data_fashion():
    path = "/Users/muditjoshi/fashion/t/"
    data_arr = []
    for filename in os.listdir(path):
        print("Processing : ", filename)

        with open(path + filename) as f:
            i = 0
            for line in f:
                i += 1
                if i > 1000:
                    break
                l = json.loads(line)
                lst_mp = {}
                sz_mp = {}
                data = {}
                for key in l.keys():
                    if key in comps:
                        if key in ['category', 'bundle_category']:
                            if 'category' not in data.keys():
                                data["category"] = l[key]
                        elif key in ['subcategory', 'bundle_subcategory']:
                            if 'subCategory' not in data.keys():
                                data["subCategory"] = l[key]
                        elif key in ['features', 'product_features', 'prod_features']:
                            if 'features' in data.keys():
                                data["features"] += l[key]
                            else:
                                data["features"] = l[key]
                        elif key == 'images':
                            if isinstance(l[key], str):
                                s = str(l[key][2:-2])
                                lst = s.split('", "')
                                for j in lst:
                                    if j is not "":
                                        lst_mp[len(lst_mp)] = j
                            else:
                                for j in l[key]:
                                    lst_mp[len(lst_mp)] = j
                        elif key in ['thumbnail', 'image_temp']:
                            lst_mp[len(lst_mp)] = l[key]
                        elif key in ['condition', 'product_condition']:
                            if 'condition' in data.keys():
                                data["condition"] += l[key]
                            else:
                                data["condition"] = l[key]
                        elif key == 'model_no':
                            data['modelNo'] = l[key]
                        elif key == 'product_type':
                            data['productType'] = l[key]
                        elif key == 'shipping_details':
                            data['shippingDetails'] = l[key]
                        elif key == 'no_reviews':
                            data['numberOfReviews'] = l[key]
                        elif key in ['avg_rating', 'no_ratings']:
                            data['avgRating'] = l[key]
                        elif key in ['final_url', 'url', 'canonical_url']:
                            if 'finalUrl' not in data.keys():
                                data["finalUrl"] = l[key]
                        elif key == 'all_sizes':
                            for sz in l[key]:
                                sz_mp[len(sz_mp)] = sz
                        elif key == 'available_price':
                            if 'price' not in data.keys():
                                data['price'] = l[key]
                        elif key == 'mrp':
                            data['price'] = l[key]
                        elif key == 'shipping_price':
                            if 'price' in data.keys():
                                data['price'] += l[key]
                        else:
                            data[key] = l[key]
                data['images'] = lst_mp
                data['sizes'] = sz_mp
                data_arr.append(data)
    res = requests.post('http://localhost:8080/mongo/addall', json=data_arr)
    response = app.response_class(
        response=res.json(),
        status=200,
        mimetype='application/json'
    )
    return response


@app.route('/upload_tech')
def upload_data_tech():
    path = "/Users/muditjoshi/electronics/t/"
    data_arr = []
    for filename in os.listdir(path):
        print("Processing : ", filename)

        with open(path + filename) as f:
            i = 0
            for line in f:
                i += 1
                if i > 1000:
                    break
                l = json.loads(line)
                lst_mp = {}
                sz_mp = {}
                data = {}
                for key in l.keys():
                    if key in comps:
                        if key in ['category', 'bundle_category']:
                            if 'category' not in data.keys():
                                data["category"] = l[key]
                        elif key in ['subcategory', 'bundle_subcategory']:
                            if 'subCategory' not in data.keys():
                                data["subCategory"] = l[key]
                        elif key in ['features', 'product_features', 'prod_features']:
                            if 'features' in data.keys():
                                data["features"] += l[key]
                            else:
                                data["features"] = l[key]
                        elif key == 'images':
                            if isinstance(l[key], str):
                                s = str(l[key][2:-2])
                                lst = s.split('", "')
                                for j in lst:
                                    if j is not "":
                                        lst_mp[len(lst_mp)] = j
                            else:
                                for j in l[key]:
                                    lst_mp[len(lst_mp)] = j
                        elif key in ['thumbnail', 'image_temp']:
                            lst_mp[len(lst_mp)] = l[key]
                        elif key in ['condition', 'product_condition']:
                            if 'condition' in data.keys():
                                data["condition"] += l[key]
                            else:
                                data["condition"] = l[key]
                        elif key == 'model_no':
                            data['modelNo'] = l[key]
                        elif key == 'product_type':
                            data['productType'] = l[key]
                        elif key == 'shipping_details':
                            data['shippingDetails'] = l[key]
                        elif key == 'no_reviews':
                            data['numberOfReviews'] = l[key]
                        elif key in ['avg_rating', 'no_ratings']:
                            data['avgRating'] = l[key]
                        elif key in ['final_url', 'url', 'canonical_url']:
                            if 'finalUrl' not in data.keys():
                                data["final_url"] = l[key]
                        elif key == 'all_sizes':
                            for sz in l[key]:
                                sz_mp[len(sz_mp)] = sz
                        else:
                            data[key] = l[key]
                data['images'] = lst_mp
                data['sizes'] = sz_mp
                data_arr.append(data)
    res = requests.post('http://localhost:8080/mongo/addall', json=data_arr)
    response = app.response_class(
        response=res.json(),
        status=200,
        mimetype='application/json'
    )
    return response

def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False


@app.route('/')
def hello_world():
    query = request.args.get('query')

    # 1.tokenize
    tokenized_query = nltk.word_tokenize(query)
    tokenized_query = [word.lower() for word in tokenized_query]
    table = str.maketrans('', '', string.punctuation)
    tokenized_query = [w.translate(table) for w in tokenized_query]
    tokenized_set = set(tokenized_query)

    # 2. Search comparison and store in operator and values
    COMPARATORS = {
        "less than": ["less", "under", "<", "lesser", "least"],
        "more than": ["more", "over", ">", "greater", "greatest"],
        "equal": ["equals", "equal"],
        "between": ["between"]
    }

    compare = {
        'operator': "",
        'values': []
    }

    # 3. values numeric
    for token in tokenized_query:
        token = [str(ele) for ele in token if ele.isdigit()]
        token = ''.join(token)
        if is_number(token):
            compare['values'].append(token)

    # 4. SpellCorrect
    spell = SpellChecker()
    misspelled = spell.unknown(tokenized_query)

    if 'tshirt' in misspelled:
        tokenized_set.add('t-shirt')
    for word in misspelled:
        if word != 'tshirt':
            tokenized_set.remove(word)
            # Get the one `most likely` answer
            tokenized_set.add(spell.correction(word))

    tokenized_query = list(tokenized_set)

    operatorFound = False
    for token in tokenized_query:
        if not operatorFound:
            for key, value in COMPARATORS.items():
                if token in value:
                    compare['operator'] = key

    # 5. remove punctuation
    tokenized_query = [word for word in tokenized_query if word.isalpha()]

    # 6. remove stop words
    stop_words = set(stopwords.words('english'))
    tokenized_query = [w for w in tokenized_query if not w in stop_words]
    print(tokenized_query)
    filteredQuery = ' '.join(tokenized_query)
    url = 'http://10.105.25.45:8080/search/suggestion?query=' + filteredQuery
    print("Query: "+filteredQuery)
    result = requests.get(url)
    return result.text


@app.route('/getAllProducts')
def getAllProducts():
    data = ({
            'productName': 'T-Shirt',
            'color': 'red'
        })
    return data
