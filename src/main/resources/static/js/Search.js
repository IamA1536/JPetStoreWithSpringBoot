$(document).ready(function () {
    autoComplete()
})

function autoComplete() {
    var itemList= ["Amazon Parrot",
        "Finch",
        "Koi",
        "Goldfish",
        "Angelfish",
        "Tiger Shark",
        "Persian",
        "Manx",
        "Bulldog",
        "Chihuahua",
        "Dalmation",
        "Poodle",
        "Golden Retriever",
        "Labrador Retriever",
        "Iguana",
        "Rattlesnake"]
        $("#SearchInput").autocomplete({
            source: function (request, response) {
                var results = $.ui.autocomplete.filter(itemList, request.term)
                response(results.slice(0, 5))
            },
            messages: {
                noResults: '',
                results: function () {
                }
            },
        })
}

