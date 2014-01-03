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
		}
		
	]
}