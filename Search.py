from flask import Flask, request
import nltk
import requests
from spellchecker import SpellChecker
from nltk.corpus import stopwords
import string
app = Flask(__name__)


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
