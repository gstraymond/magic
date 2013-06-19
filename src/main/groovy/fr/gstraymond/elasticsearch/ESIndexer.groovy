package fr.gstraymond.elasticsearch

import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import java.text.Normalizer

class ESIndexer {
	def host
	def port = 9200
	def protocol = 'http'

	def getRestClient() {
		new RESTClient("${protocol}://${host}:${port}/")
	}

	void index(obj) {
		try {
			def jsonBuilder = new JsonBuilder(obj)
			def resp = restClient.post(
					path: "magic/card/${getId(obj)}",
					body: jsonBuilder.toPrettyString(),
					requestContentType : JSON)
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	String getId(obj) {
		def link = Normalizer.normalize(obj.title, Normalizer.Form.NFKD)
		link = link.replace(' ', '-').replaceAll(/[^-\w]/, '')
		link.toLowerCase()
	}

	void clear() {
		try {
			def resp = restClient.delete(path: 'magic')
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	void configure() {
		try {
			println "configuring..."
			println getESConfiguration()
			def resp = restClient.put(
					path: 'magic',
					body: getESConfiguration().toString(),
					requestContentType : JSON)
		} catch(HttpResponseException e) {
			println e.response.data
			e.printStackTrace();
		}
	}

	def getESConfiguration() {
		def builder = new JsonBuilder()
		builder.mappings {
			card {
				properties {
					editions {
						type 'multi_field'
						fields {
							editions {
								type 'string'
								index 'analyzed'
							}
							exact {
								type 'string'
								index 'not_analyzed'
							}
						}
					}
					abilities {
						type 'multi_field'
						fields {
							abilities {
								type 'string'
								index 'analyzed'
							}
							exact {
								type 'string'
								index 'not_analyzed'
							}
						}
					}
					title {
						type 'multi_field'
						fields {
							title {
								type 'string'
								index 'analyzed'
							}
							exact {
								type 'string'
								index 'not_analyzed'
							}
						}
					}
					priceRanges {
						type 'multi_field'
						fields {
							priceRanges {
								type 'string'
								index 'analyzed'
							}
							exact {
								type 'string'
								index 'not_analyzed'
							}
						}
					}
				}
			}
		}
		builder
	}
}