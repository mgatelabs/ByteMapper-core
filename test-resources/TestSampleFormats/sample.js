{
	"name": "Sample Format",
	"description": "Just a test",
	"min": "1",
	"max": "1",
	"tags": [
		{
			"name": "Sample1",
			"description": "",
			"identity": "1",
			"min": "1",
			"max": "0",
			"fields": [
				{
					"name": "int1",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "int2",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "int3",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "int4",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "int5",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				}
			],
			"properties": {
			}
		},

		{
			"name": "Sample2",
			"description": "",
			"identity": "2",
			"min": "1",
			"max": "0",
			"fields": [
				{
					"name": "object1",
					"description": "",
					"type": "tag/Sample1",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "int1",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "long1",
					"description": "",
					"type": "lng",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "string1",
					"description": "",
					"type": "string",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				},
				{
					"name": "bytes",
					"description": "",
					"type": "blob",
					"min": "1",
					"max": "0",
					"required": "true",
					"properties": {
					
					}
				}
			],
			"properties": {
			}
		},

		{
			"name": "Sample3",
			"description": "",
			"identity": "3",
			"min": "1",
			"max": "0",
			"fields": [
				{
					"name": "link1",
					"description": "",
					"type": "filelink",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
						
					}
				},
				{
					"name": "link2",
					"description": "",
					"type": "filelink",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {

					}
				},
				{
					"name": "link3",
					"description": "",
					"type": "filelink",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
						"compress": "true"
					}
				},
				{
					"name": "string1",
					"description": "",
					"type": "string",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
					
					}
				},
				{
					"name": "string2",
					"description": "",
					"type": "string",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
					
					}
				},
				{
					"name": "string3",
					"description": "",
					"type": "string",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
					
					}
				}
			],
			"properties": {
			}
		},

		{
			"name": "Sample4",
			"description": "",
			"identity": "4",
			"min": "1",
			"max": "0",
			"fields": [
				{
					"name": "int1",
					"description": "",
					"type": "int",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
						
					}
				},
				{
					"name": "int2",
					"description": "",
					"type": "integer",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {

					}
				},
				{
					"name": "long1",
					"description": "",
					"type": "lng",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
					
					}
				},
				{
					"name": "long2",
					"description": "",
					"type": "long",
					"min": "1",
					"max": "0",
					"required": "false",
					"properties": {
					
					}
				}
			],
			"properties": {
			}
		},
		
		{
         			"name": "Sample5",
         			"description": "",
         			"identity": "5",
         			"min": "1",
         			"max": "0",
         			"fields": [
         				{
         					"name": "list1",
         					"description": "",
         					"type": "list",
         					"min": "1",
         					"max": "0",
         					"required": "false",
         					"properties": {

         					}
         				},
         				{
         					"name": "list2",
         					"description": "",
         					"type": "list/integer",
         					"min": "1",
         					"max": "0",
         					"required": "false",
         					"properties": {

         					}
         				},
         				{
         					"name": "list3",
         					"description": "",
         					"type": "list/Sample4",
         					"min": "1",
         					"max": "0",
         					"required": "false",
         					"properties": {

         					}
         				}
         			],
         			"properties": {
         			}
         		},

         		{
                    "name": "Sample6",
                    "description": "Test Enums",
                    "identity": "6",
                    "min": "1",
                    "max": "0",
                    "fields": [
                        {
                            "name": "enum1",
                            "description": "",
                            "type": "enum",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "enum2",
                            "description": "",
                            "type": "enum",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "enum3",
                            "description": "",
                            "type": "enum",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        }
                    ],
                    "properties": {
                    }
                },

                {
                    "name": "Sample7",
                    "description": "Test Dates",
                    "identity": "7",
                    "min": "1",
                    "max": "0",
                    "fields": [
                        {
                            "name": "d1",
                            "description": "",
                            "type": "date",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "d2",
                            "description": "",
                            "type": "date",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "d3",
                            "description": "",
                            "type": "date",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        }
                    ],
                    "properties": {
                    }
                },

                {
                    "name": "NoiseSample",
                    "description": "Data with noise",
                    "identity": "8",
                    "min": "1",
                    "max": "0",
                    "fields": [
                        {
                            "name": "-",
                            "description": "",
                            "type": "noise",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {
                                "min":"5",
                                "max":"10"
                            }
                        },
                        {
                            "name": "-",
                            "description": "",
                            "type": "noise",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {
                                "min":"5",
                                "max":"10"
                            }
                        },
                        {
                            "name": "-",
                            "description": "",
                            "type": "noise",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {
                                "min":"5",
                                "max":"100"
                            }
                        },
                        {
                            "name": "sample",
                            "description": "",
                            "type": "string",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "-",
                            "description": "",
                            "type": "noise",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {
                                "min":"5",
                                "max":"10"
                            }
                        }
                    ],
                    "properties": {
                    }
                },

                {
                    "name": "SampleFileLink",
                    "description": "Data with links",
                    "identity": "9",
                    "min": "1",
                    "max": "0",
                    "fields": [

                        {
                            "name": "f1",
                            "description": "",
                            "type": "filelink",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "f2",
                            "description": "",
                            "type": "filelink",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {

                            }
                        },
                        {
                            "name": "f3",
                            "description": "",
                            "type": "filelink",
                            "min": "1",
                            "max": "0",
                            "required": "false",
                            "properties": {
                                "compress": "true"
                            }
                        }

                    ],
                    "properties": {
                    }
                }
		
	]
}