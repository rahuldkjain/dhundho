import json
import os
import requests
from flask import Flask

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
